package com.bed.seller.data.usecases.auth

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.AuthClient

import com.bed.seller.domain.alias.AuthEitherEntityType
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.auth.AuthUseCase

import com.bed.seller.domain.entities.ResponseEntity

import com.bed.seller.infrastructure.network.models.responses.auth.toEntity
import com.bed.seller.infrastructure.network.models.responses.failure.toEntity

class RemoteSignUpUseCase(
    private val authClient: AuthClient,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : AuthUseCase, UseCase<AuthUseCase.Params, AuthEitherEntityType>() {
        override suspend fun doWork(params: AuthUseCase.Params): AuthEitherEntityType =
        withContext(coroutinesDispatchers.io()) {
            return@withContext authClient(params.path, params.body)
                .map { success -> ResponseEntity(success.status, success.data.toEntity()) }
                .mapLeft { failure -> ResponseEntity(failure.status, failure.data.toEntity()) }
        }
}
