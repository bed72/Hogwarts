package com.bed.core.data.repositories.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow

import com.bed.core.data.datasources.storage.StorageDataSource

interface StorageRepository {
    suspend fun get(value: String): Flow<String>

    suspend fun save(value: Pair<String, String>)
}

class StorageRepositoryImpl @Inject constructor(
    private val datasource: StorageDataSource
) : StorageRepository {

    override suspend fun get(value: String): Flow<String> = datasource.get(value)

    override suspend fun save(value: Pair<String, String>) { datasource.save(value) }
}
