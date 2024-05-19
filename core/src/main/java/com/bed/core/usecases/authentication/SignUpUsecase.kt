package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase

import com.bed.core.data.repositories.CoroutinesRepository
import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.core.domain.alias.AuthenticationType
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

interface SignUpUsecase {
    operator fun invoke(parameter: AuthenticationParameter): Flow<AuthenticationType>
}

class SignUpUsecaseImpl @Inject constructor(
    private val coroutinesRepository: CoroutinesRepository,
    private val authenticationRepository: AuthenticationRepository,
) : SignUpUsecase, UseCase<AuthenticationParameter, AuthenticationType>() {
    override suspend fun doWork(parameter: AuthenticationParameter): AuthenticationType =
        withContext(coroutinesRepository.io()) { authenticationRepository.signUp(parameter) }
}