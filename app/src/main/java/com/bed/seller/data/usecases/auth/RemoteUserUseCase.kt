package com.bed.seller.data.usecases.auth

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.auth.UserClient
import com.bed.seller.data.client.storage.StorageClient

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.auth.UserUseCase

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.entities.ResponseEntity
import com.bed.seller.domain.alias.UserEitherEntityType

import com.bed.seller.infrastructure.storage.StorageConstants
import com.bed.seller.infrastructure.network.models.failure.toEntity
import com.bed.seller.infrastructure.network.models.auth.user.toEntity
import com.bed.seller.infrastructure.network.models.auth.user.UserResponseModel

class RemoteUserUseCase(
    private val coroutines: Coroutines,
    private val userClient: UserClient,
    private val storageClient: StorageClient
) : UserUseCase, UseCase<UserUseCase.Params, UserEitherEntityType>() {
    override suspend fun doWork(params: UserUseCase.Params): UserEitherEntityType =
        withContext(coroutines.io()) {
            return@withContext userClient(params.path)
                .map { success ->
                    with(success) {
                        save(success.data)

                        ResponseEntity(status, data.toEntity())
                    }
                }
                .mapLeft { failure -> ResponseEntity(failure.status, failure.data.toEntity()) }
        }

    private suspend fun save(data: UserResponseModel) {
        storageClient.saveData(StorageConstants.DATA_STORE_NAME to data.userMetadata.name)
    }
}


