package com.bed.seller.data.usecases.storage

import kotlinx.coroutines.flow.Flow

import com.bed.seller.data.client.StorageClient

import com.bed.seller.domain.usecases.storage.StorageUseCase

class LocalStorageUseCase(
    private val storageClient: StorageClient
) : StorageUseCase {
    override suspend fun get(key: String): Flow<String> = storageClient.get(key)

    override suspend fun save(key: String, value: String) {
        storageClient.save(key, value)
    }
}
