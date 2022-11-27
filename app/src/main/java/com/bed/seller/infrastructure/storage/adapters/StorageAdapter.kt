package com.bed.seller.infrastructure.storage.adapters

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.stringPreferencesKey

import com.bed.seller.data.client.storage.StorageClient
import com.bed.seller.data.client.security.SecurityClient

import com.bed.seller.infrastructure.storage.StorageConstants
import com.bed.seller.infrastructure.configuration.SecurityJson

class StorageAdapter(
    private val client: SecurityClient,
    private val dataStore: DataStore<Preferences>,
) : StorageClient {

    override suspend fun clearData() {
        dataStore.edit { preferences -> preferences.clear() }
    }

    override fun getData(params: String) =
        dataStore.data.map { preferences ->
            preferences[buildPreferencesKey(params)].orEmpty()
        }

    override suspend fun saveData(params: Pair<String, String>) {
        with(dataStore) {
            edit { preferences -> preferences[buildPreferencesKey(params.first)] = params.second }
        }
    }

    override fun getSecureData(params: String): Flow<String> =
        if (params == ACCESS_TOKEN) getSecureSmallData(params)
        else getSecureLargeData(params)

    override suspend fun saveSecureData(params: Pair<String, String>) {
        if (params.first == ACCESS_TOKEN) saveSecureLargeData(params)
        else saveSecureSmallData(params)
    }

    private fun buildPreferencesKey(key: String): Preferences.Key<String> =
        stringPreferencesKey(key)

    private fun getSecureSmallData(params: String) =
        dataStore.data.secureMap { preferences ->
            preferences[buildPreferencesKey(params)].orEmpty()
        }

    private suspend fun saveSecureSmallData(params: Pair<String, String>) {
        with(dataStore) {
            secureEdit(params.second) { preferences, encrypted ->
                preferences[buildPreferencesKey(params.first)] = encrypted
            }
        }
    }

    @Suppress("UnusedPrivateMember")
    private fun getSecureLargeData(params: String) = flowOf("")

    private suspend fun saveSecureLargeData(params: Pair<String, String>) {
        var parts = 0
        var indices = 0
        var breakSteps = 19

        val steps = 20
        val values = mutableListOf<String>()

        do {
            if (parts == breakSteps) {
                values.add(params.second.slice(indices until breakSteps))
                parts = breakSteps
                indices = parts
                breakSteps += steps
            }

            parts ++
        } while (parts < params.second.length)

        for ((index, data) in values.withIndex()) {
            saveSecureSmallData("${params.first}_$index" to data)
        }
    }

    private suspend inline fun DataStore<Preferences>.secureEdit(
        value: String,
        crossinline store: (MutablePreferences, String) -> Unit
    ) {
        edit { mutablePreferences ->
            val encrypted = client.encrypt(Json.encodeToString(value))

            store(
                mutablePreferences,
                encrypted.joinToString(StorageConstants.DATA_STORE_SEPARATOR)
            )
        }
    }

    private inline fun Flow<Preferences>.secureMap(
        crossinline fetch: (value: Preferences) -> String
    ): Flow<String> = map { preferences ->
        if (fetch(preferences).isEmpty()) return@map ""

        val decrypted = client.decrypt(
            fetch(preferences).split(StorageConstants.DATA_STORE_SEPARATOR).map { it.toByte() }
                .toByteArray()
        )

        SecurityJson.Config.decodeFromString(decrypted)
    }

    companion object {
        private const val ACCESS_TOKEN = StorageConstants.DATA_STORE_ACCESS_TOKEN
    }
}
