package com.bed.seller.infrastructure.storage.adapters

import android.content.Context

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey

import com.bed.seller.data.client.StorageClient
import com.bed.seller.infrastructure.storage.StorageConstants

class StorageAdapter(private val context: Context) : StorageClient {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = StorageConstants.DATA_STORE_NAME,
    )

    override suspend fun get(key: String): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[buildPreferencesKey(key)] ?: ""
        }

    override suspend fun save(key: String, value: String) {
        context.dataStore.edit { preferences ->
            preferences[buildPreferencesKey(key)] = value
        }
    }

    private fun buildPreferencesKey(key: String): Preferences.Key<String> =
        stringPreferencesKey(key)
}
