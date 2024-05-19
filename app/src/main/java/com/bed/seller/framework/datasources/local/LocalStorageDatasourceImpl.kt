package com.bed.seller.framework.datasources.local

import javax.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

import androidx.datastore.core.DataStore

import com.bed.seller.data.datasources.StorageDatasource

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

class LocalStorageDatasourceImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) : StorageDatasource {

    override suspend fun get(value: String): Flow<String> =
        datastore.data.map { preferences -> preferences[buildKey(value)].orEmpty() }

    override suspend fun save(value: Pair<String, String>) {
        datastore.edit { preferences -> preferences[buildKey(value.first)] = value.second }
    }

    private fun buildKey(value: String): Preferences.Key<String> = stringPreferencesKey(value)
}
