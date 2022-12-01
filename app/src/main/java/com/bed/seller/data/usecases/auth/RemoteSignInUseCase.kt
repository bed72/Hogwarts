package com.bed.seller.data.usecases.auth

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.auth.SignInClient
import com.bed.seller.data.client.storage.StorageClient

import com.bed.seller.domain.entities.ResponseEntity

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.alias.AuthEitherEntityType

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.auth.SignInUseCase

import com.bed.seller.infrastructure.storage.StorageConstants
import com.bed.seller.infrastructure.network.models.auth.toEntity
import com.bed.seller.infrastructure.network.models.failure.toEntity

class RemoteSignInUseCase(
    private val coroutines: Coroutines,
    private val signInClient: SignInClient,
    private val storageClient: StorageClient
) : SignInUseCase, UseCase<SignInUseCase.Params, AuthEitherEntityType>() {
        override suspend fun doWork(params: SignInUseCase.Params): AuthEitherEntityType =
            withContext(coroutines.io()) {
                return@withContext signInClient(params.path, params.body)
                    .map { success ->
                        with (success) {
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

    private suspend fun save(vararg data: Pair<String, String>)  {
        for (value in data) storageClient.saveData(value.first to value.second)
    }
}
