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

class StorageAdapter(private val dataStore: DataStore<Preferences>) : StorageClient {

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

    private fun buildPreferencesKey(key: String): Preferences.Key<String> =
        stringPreferencesKey(key)
}
