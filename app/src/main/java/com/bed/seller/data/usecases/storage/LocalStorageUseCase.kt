package com.bed.seller.data.usecases.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.seller.data.client.storage.StorageClient

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.storage.StorageUseCase

class LocalStorageUseCase(
    private val client: StorageClient,
    private val coroutines: Coroutines
) : StorageUseCase {
    override suspend fun clearDataStore() =
        withContext(coroutines.io()) { client.clearDataStore() }

    override suspend fun get(params: String): Flow<String> =
        withContext(coroutines.io()) { client.get(params) }
    override suspend fun save(params: Pair<String, String>): Flow<String> =
        withContext(coroutines.io()) { client.save(params) }

    override suspend fun getSecure(params: String): Flow<String>  =
        withContext(coroutines.io()) { client.getSecure(params) }
    override suspend fun saveSecure(params: Pair<String, String>): Flow<String> =
        withContext(coroutines.io()) { client.saveSecure(params) }
}
