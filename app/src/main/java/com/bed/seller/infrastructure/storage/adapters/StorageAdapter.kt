package com.bed.seller.infrastructure.storage.adapters

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

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

    override suspend fun clearDataStore() {
        dataStore.edit { preferences -> preferences.clear() }
    }

    override suspend fun get(params: String) =
        dataStore.data.map { preferences ->
            preferences[buildPreferencesKey(params)].orEmpty()
        }

    override suspend fun save(params: Pair<String, String>) =
        with (dataStore) {
            edit { preferences -> preferences[buildPreferencesKey(params.first)] = params.second }

            get(params.second)
        }

    override suspend fun getSecure(params: String) =
        dataStore.data.secureMap { preference ->
            preference[buildPreferencesKey(params)].orEmpty()
        }

    override suspend fun saveSecure(params: Pair<String, String>) =
        with (dataStore) {
            secureEdit(params.second) { preferences, encrypted ->
                preferences[buildPreferencesKey(params.first)] = encrypted
            }

            get(params.second)
        }

    private fun buildPreferencesKey(key: String): Preferences.Key<String> =
        stringPreferencesKey(key)

    private suspend inline fun DataStore<Preferences>.secureEdit(
        value: String,
        crossinline store: (MutablePreferences, String) -> Unit
    ) {
        edit { mutablePreferences ->
            val encrypted = client.encrypt(
                StorageConstants.DATA_STORE_NAME, Json.encodeToString(value)
            )

            store(
                mutablePreferences,
                encrypted.joinToString(StorageConstants.DATA_STORE_SEPARATOR)
            )
        }
    }

    private inline fun Flow<Preferences>.secureMap(
        crossinline fetch: (value: Preferences) -> String
    ): Flow<String> = map { preferences ->
       // if (fetch(preferences).isEmpty()) return@map ""

        val decrypted = client.decrypt(
            StorageConstants.DATA_STORE_NAME,
            fetch(preferences).split(StorageConstants.DATA_STORE_SEPARATOR).map { it.toByte() }
                .toByteArray()
        )

        SecurityJson.Config.decodeFromString(decrypted)
    }
}
