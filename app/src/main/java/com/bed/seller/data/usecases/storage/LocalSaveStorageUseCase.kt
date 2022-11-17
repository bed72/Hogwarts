package com.bed.seller.data.usecases.storage

import arrow.core.Either

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.storage.StorageClient

import com.bed.seller.domain.alias.ResponseStorageType
import com.bed.seller.domain.dispatchers.Coroutines

import com.bed.seller.domain.usecases.FlowUseCase
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase

class LocalSaveStorageUseCase(
    private val storageClient: StorageClient,
    private val coroutinesDispatchers: Coroutines
) : SaveStorageUseCase, FlowUseCase<Pair<String, String>, Either<String, String>>() {
    override suspend fun createFlowObservable(params: Pair<String, String>): ResponseStorageType =
        withContext(coroutinesDispatchers.io()) {
            storageClient.save(params)
        }
}
