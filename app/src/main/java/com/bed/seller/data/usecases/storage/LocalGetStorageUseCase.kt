package com.bed.seller.data.usecases.storage

import arrow.core.Either

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.StorageClient

import com.bed.seller.domain.alias.ResponseStorageType
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.FlowUseCase
import com.bed.seller.domain.usecases.storage.GetStorageUseCase

class LocalGetStorageUseCase(
    private val storageClient: StorageClient,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : GetStorageUseCase, FlowUseCase<String, Either<String, String>>() {

    override suspend fun createFlowObservable(params: String): ResponseStorageType =
        withContext(coroutinesDispatchers.io()) {
            storageClient.get(params)
        }
}
