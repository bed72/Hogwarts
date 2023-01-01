package com.bed.seller.data.usecases.auth

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.auth.UserClient
import com.bed.seller.data.alias.UserEitherEntityType

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.auth.UserUseCase

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.entities.ResponseEntity

import com.bed.seller.infrastructure.network.models.failure.toEntity
import com.bed.seller.infrastructure.network.models.auth.user.toEntity

class RemoteUserUseCase(
    private val coroutines: Coroutines,
    private val userClient: UserClient,
) : UserUseCase, UseCase<UserUseCase.Params, UserEitherEntityType>() {
    override suspend fun doWork(params: UserUseCase.Params): UserEitherEntityType =
        withContext(coroutines.io()) {
            return@withContext userClient(params.path)
                .map { success -> ResponseEntity(success.status, success.data.toEntity()) }
                .mapLeft { failure -> ResponseEntity(failure.status, failure.data.toEntity()) }
        }
}


