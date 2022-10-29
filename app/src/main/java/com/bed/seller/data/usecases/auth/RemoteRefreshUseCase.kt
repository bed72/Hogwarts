package com.bed.seller.data.usecases.auth

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.AuthRefreshClient

import com.bed.seller.domain.entities.ResponseEntity

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.auth.AuthRefreshUseCase

import com.bed.seller.domain.alias.AuthEitherEntityType
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.infrastructure.network.models.auth.toEntity
import com.bed.seller.infrastructure.network.models.failure.toEntity

class RemoteRefreshUseCase(
    private val client: AuthRefreshClient,
    private val coroutines: CoroutinesDispatchers
) : AuthRefreshUseCase, UseCase<AuthRefreshUseCase.Params, AuthEitherEntityType>() {
        override suspend fun doWork(params: AuthRefreshUseCase.Params): AuthEitherEntityType =
            withContext(coroutines.io()) {
                return@withContext client(params.path, params.body)
                    .map { success -> ResponseEntity(success.status, success.data.toEntity()) }
                    .mapLeft { failure -> ResponseEntity(failure.status, failure.data.toEntity()) }
            }
}
