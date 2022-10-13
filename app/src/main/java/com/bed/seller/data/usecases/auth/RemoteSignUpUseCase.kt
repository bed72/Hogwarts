package com.bed.seller.data.usecases.auth

import kotlinx.coroutines.withContext

import com.bed.seller.data.client.SignUpClient

import com.bed.seller.domain.alias.AuthEitherEntityType
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.UseCase
import com.bed.seller.domain.usecases.auth.signup.SignUpUseCase

import com.bed.seller.domain.entities.ResponseEntity
import com.bed.seller.domain.entities.auth.AuthRequestEntity

import com.bed.seller.infrastructure.network.models.responses.auth.toEntity
import com.bed.seller.infrastructure.network.models.responses.failure.toEntity

class RemoteSignUpUseCase(
    private val signUpClient: SignUpClient,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : SignUpUseCase, UseCase<AuthRequestEntity, AuthEitherEntityType>() {
    override suspend fun doWork(params: AuthRequestEntity): AuthEitherEntityType =
        withContext(coroutinesDispatchers.io()) {
            return@withContext signUpClient.signUp(params)
                .map { success -> ResponseEntity(success.status, success.data.toEntity()) }
                .mapLeft { failure -> ResponseEntity(failure.status, failure.data.toEntity()) }
        }
}
