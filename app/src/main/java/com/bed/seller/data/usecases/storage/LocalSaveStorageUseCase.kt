package com.bed.seller.data.usecases.storage

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.StorageClient

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase

class LocalSaveStorageUseCase(
    private val storageClient: StorageClient,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : SaveStorageUseCase, UseCase<SaveStorageUseCase.Params, Unit>() {
    override suspend fun doWork(params: SaveStorageUseCase.Params) =
        withContext(coroutinesDispatchers.io()) {
            storageClient.save(params.data)
        }
}
