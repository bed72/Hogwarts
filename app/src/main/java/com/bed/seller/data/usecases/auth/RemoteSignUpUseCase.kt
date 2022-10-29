package com.bed.seller.data.usecases.auth

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.AuthSignUpClient

import com.bed.seller.domain.entities.ResponseEntity

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.auth.AuthSignUpUseCase

import com.bed.seller.domain.alias.AuthEitherEntityType
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.infrastructure.network.models.auth.toEntity
import com.bed.seller.infrastructure.network.models.failure.toEntity

class RemoteSignUpUseCase(
    private val client: AuthSignUpClient,
    private val coroutines: CoroutinesDispatchers
) : AuthSignUpUseCase, UseCase<AuthSignUpUseCase.Params, AuthEitherEntityType>() {
        override suspend fun doWork(params: AuthSignUpUseCase.Params): AuthEitherEntityType =
            withContext(coroutines.io()) {
                return@withContext client(params.path, params.body)
                    .map { success -> ResponseEntity(success.status, success.data.toEntity()) }
                    .mapLeft { failure -> ResponseEntity(failure.status, failure.data.toEntity()) }
            }
}
