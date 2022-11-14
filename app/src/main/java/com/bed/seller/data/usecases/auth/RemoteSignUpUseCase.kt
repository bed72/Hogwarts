package com.bed.seller.data.usecases.auth

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.storage.StorageClient
import com.bed.seller.data.client.auth.SignUpClient

import com.bed.seller.domain.entities.ResponseEntity

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.auth.SignUpUseCase

import com.bed.seller.domain.alias.AuthEitherEntityType
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.infrastructure.storage.StorageConstants
import com.bed.seller.infrastructure.network.models.auth.toEntity
import com.bed.seller.infrastructure.network.models.failure.toEntity

class RemoteSignUpUseCase(
    private val storageClient: StorageClient,
    private val signUpClient: SignUpClient,
    private val coroutines: CoroutinesDispatchers
) : SignUpUseCase, UseCase<SignUpUseCase.Params, AuthEitherEntityType>() {
        override suspend fun doWork(params: SignUpUseCase.Params): AuthEitherEntityType =
            withContext(coroutines.io()) {
                return@withContext signUpClient(params.path, params.body)
                    .map { success ->
                        with (success) {
                            save(
                                StorageConstants.DATA_STORE_ACCESS_TOKEN to data.accessToken,
                                StorageConstants.DATA_STORE_REFRESH_TOKEN to data.refreshToken,
                            )

                            ResponseEntity(status, data.toEntity())
                        }
                    }
                    .mapLeft { failure -> ResponseEntity(failure.status, failure.data.toEntity()) }
            }

    private suspend fun save(vararg data: Pair<String, String>)  {
        for (value in data) storageClient.save(value.first to value.second)
    }
}
