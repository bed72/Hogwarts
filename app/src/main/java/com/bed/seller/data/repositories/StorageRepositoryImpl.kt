package com.bed.seller.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow

import com.bed.seller.data.datasources.StorageDatasource

interface StorageRepository {
    suspend fun get(value: String): Flow<String>

    suspend fun save(value: Pair<String, String>)
}

class StorageRepositoryImpl @Inject constructor(
    private val datasource: StorageDatasource
) : StorageRepository {

    override suspend fun get(value: String): Flow<String> = datasource.get(value)

    override suspend fun save(value: Pair<String, String>) { datasource.save(value) }
}
