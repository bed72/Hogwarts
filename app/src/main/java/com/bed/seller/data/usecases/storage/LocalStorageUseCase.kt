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
    override suspend fun clearData() {
        withContext(coroutines.io()) { client.clearData() }
    }

    override suspend fun getData(params: String): Flow<String> =
        withContext(coroutines.io()) { client.getData(params) }

    override suspend fun saveData(params: Pair<String, String>) {
        withContext(coroutines.io()) { client.saveData(params) }
    }
}
