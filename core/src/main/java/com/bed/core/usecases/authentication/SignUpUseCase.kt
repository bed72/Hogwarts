package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.core.domain.alias.AuthenticationType
import com.bed.core.domain.parameters.authentication.SignUpParameter

interface SignUpUseCase {
    operator fun invoke(parameter: SignUpParameter): Flow<AuthenticationType>
}

class SignUpUseCaseImpl @Inject constructor(
    private val useCase: CoroutinesUseCase,
    private val repository: AuthenticationRepository,
) : SignUpUseCase, UseCase<SignUpParameter, AuthenticationType>() {
    override suspend fun doWork(parameter: SignUpParameter): AuthenticationType =
        withContext(useCase.io()) { repository.signUp(parameter) }
}
