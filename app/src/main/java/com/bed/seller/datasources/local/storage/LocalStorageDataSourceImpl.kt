package com.bed.seller.datasources.local.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences

import com.bed.core.data.datasources.storage.StorageDataSource

class LocalStorageDataSourceImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) : StorageDataSource {

    override suspend fun get(value: String): Flow<String> =
        datastore.data.map { preferences -> preferences[buildKey(value)].orEmpty() }

    override suspend fun save(value: Pair<String, String>) {
        datastore.edit { preferences -> preferences[buildKey(value.first)] = value.second }
    }
}
