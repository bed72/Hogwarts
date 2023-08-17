package com.bed.core.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow

import com.bed.core.data.datasources.local.LocalStorageDataSource

interface StorageRepository {
    suspend fun get(value: String): Flow<String>

    suspend fun save(value: Pair<String, String>)
}

class StorageRepositoryImpl @Inject constructor(
    private val datasource: LocalStorageDataSource
) : StorageRepository {

    override suspend fun get(value: String): Flow<String> = datasource.get(value)

    override suspend fun save(value: Pair<String, String>) { datasource.save(value) }
}
