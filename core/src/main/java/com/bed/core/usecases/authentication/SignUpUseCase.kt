package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.core.domain.alias.AuthenticationType
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

interface SignUpUseCase {
    operator fun invoke(parameter: AuthenticationParameter): Flow<AuthenticationType>
}

class SignUpUseCaseImpl @Inject constructor(
    private val useCase: CoroutinesUseCase,
    private val repository: AuthenticationRepository,
) : SignUpUseCase, UseCase<AuthenticationParameter, AuthenticationType>() {
    override suspend fun doWork(parameter: AuthenticationParameter): AuthenticationType =
        withContext(useCase.io()) { repository.signUp(parameter) }
}
