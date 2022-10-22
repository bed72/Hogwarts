package com.bed.seller.infrastructure.storage.adapters

import arrow.core.left
import arrow.core.right

import android.content.Context

import kotlinx.coroutines.flow.map

import androidx.datastore.core.DataStore

import com.bed.seller.data.client.StorageClient

import com.bed.seller.domain.alias.ResponseStorageType

import com.bed.seller.infrastructure.storage.StorageConstants

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey

class StorageAdapter(private val context: Context) : StorageClient {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = StorageConstants.DATA_STORE_NAME,
    )

    override suspend fun get(params: String): ResponseStorageType =
        context.dataStore.data.map { preferences ->
            val data = preferences[buildPreferencesKey(params)]

            return@map if (data.isNullOrEmpty()) "".left() else data.right()
    }


    override suspend fun save(params: Pair<String, String>): ResponseStorageType =
        with (context.dataStore) {
            edit { preferences -> preferences[buildPreferencesKey(params.first)] = params.second }

            data.map { preferences ->
                val data = preferences[buildPreferencesKey(params.first)]

                return@map if (data.isNullOrEmpty()) "".left() else data.right()
            }
        }


    private fun buildPreferencesKey(key: String): Preferences.Key<String> =
        stringPreferencesKey(key)
}
