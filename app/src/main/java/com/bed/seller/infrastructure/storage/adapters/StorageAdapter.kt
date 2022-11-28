package com.bed.seller.infrastructure.storage.adapters

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.flowOf

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

    override fun getData(params: String): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[buildPreferencesKey(params)].orEmpty()
        }

    override suspend fun saveData(params: Pair<String, String>) {
        dataStore.edit { preferences -> preferences[buildPreferencesKey(params.first)] = params.second }
    }

    override fun getSecureData(params: String): Flow<String> =
        if (params == ACCESS_TOKEN) getSecureLargeData(params)
        else getSecureSmallData(params)

    override suspend fun saveSecureData(params: Pair<String, String>) {
        if (params.first == ACCESS_TOKEN) saveSecureLargeData(params)
        else saveSecureSmallData(params)
    }

    private fun buildPreferencesKey(key: String): Preferences.Key<String> =
        stringPreferencesKey(key)

    private fun getSecureSmallData(params: String) =
        dataStore.data.secureMap<String> { preferences ->
            preferences[buildPreferencesKey(params)].orEmpty()
        }

    private suspend fun saveSecureSmallData(params: Pair<String, String>) {
        dataStore.secureEdit(params.second) { preferences, encrypted ->
                preferences[buildPreferencesKey(params.first)] = encrypted
            }
    }

    private fun getSecureLargeData(params: String): Flow<String> {
        val length = 28
        val values = mutableListOf<String>()

        runBlocking {
            for (index in 0 until length) {
                val value = getSecureSmallData("${params}_$index").first()

                values.add(value)
            }
        }

        return if (values.first().isEmpty()) flowOf("") else flowOf(values.joinToString())
    }

    private suspend fun saveSecureLargeData(params: Pair<String, String>) {
        val (restOfTokenSize, values) = handleSaveSecureLargeData(params)

        for ((index, data) in values.withIndex()) {
            saveSecureSmallData("${params.first}_$index" to data)

            if (index == (values.size - 1)) {
                saveSecureSmallData(
                    "${params.first}_${index + 1}" to
                            params.second
                                .slice(restOfTokenSize until params.second.length)
                )
            }
        }
    }

    private fun handleSaveSecureLargeData(params: Pair<String, String>): Pair<Int, MutableList<String>> {
        var parts = 0
        var indices = 0
        var breakSteps = 19

        val steps = breakSteps
        val values = mutableListOf<String>()

        while (parts < params.second.length) {
            if (parts == breakSteps) {
                values.add(params.second.slice(indices until breakSteps))
                indices = parts
                breakSteps += steps
            }

            parts++
        }

        return (breakSteps - steps) to values
    }

    private suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T,
        crossinline store: (MutablePreferences, String) -> Unit
    ) {
        edit { preferences ->
            val encrypted = client.encrypt(
                SecurityJson.Config.encodeToString(value).replace("\"", "")
            )

            store(
                preferences,
                encrypted.joinToString(StorageConstants.DATA_STORE_SEPARATOR)
            )
        }
    }

    private inline fun <reified T> Flow<Preferences>.secureMap(
        crossinline fetch: (value: Preferences) -> String
    ): Flow<T> = map { preferences ->
        if (fetch(preferences).isEmpty()) return@map "" as T

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
