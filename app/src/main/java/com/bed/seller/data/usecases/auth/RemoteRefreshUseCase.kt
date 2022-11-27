package com.bed.seller.data.usecases.auth

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.storage.StorageClient
import com.bed.seller.data.client.auth.RefreshClient

import com.bed.seller.domain.entities.ResponseEntity

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.auth.RefreshUseCase

import com.bed.seller.domain.alias.AuthEitherEntityType
import com.bed.seller.domain.dispatchers.Coroutines

import com.bed.seller.infrastructure.storage.StorageConstants
import com.bed.seller.infrastructure.network.models.auth.toEntity
import com.bed.seller.infrastructure.network.models.failure.toEntity

class RemoteRefreshUseCase(
    private val coroutines: Coroutines,
    private val refreshClient: RefreshClient,
    private val storageClient: StorageClient
) : RefreshUseCase, UseCase<RefreshUseCase.Params, AuthEitherEntityType>() {
    override suspend fun doWork(params: RefreshUseCase.Params): AuthEitherEntityType =
        withContext(coroutines.io()) {
            return@withContext refreshClient(params.path, params.body)
                .map { success ->
                    with(success) {
                        save(
                            StorageConstants.DATA_STORE_ACCESS_TOKEN to data.accessToken,
                            StorageConstants.DATA_STORE_REFRESH_TOKEN to data.refreshToken,
                            StorageConstants.DATA_STORE_NAME to data.user.userMetadata.name
                        )

                        ResponseEntity(status, data.toEntity())
                    }
                }
                .mapLeft { failure -> ResponseEntity(failure.status, failure.data.toEntity()) }
        }

    private suspend fun save(vararg data: Pair<String, String>) {
        for (value in data) storageClient.saveSecureData(value.first to value.second)
    }
}
