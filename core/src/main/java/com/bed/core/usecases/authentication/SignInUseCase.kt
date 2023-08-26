package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.core.domain.alias.AuthenticationType
import com.bed.core.domain.parameters.authentication.SignInParameter

interface SignInUseCase {
    operator fun invoke(parameter: SignInParameter): Flow<AuthenticationType>
}

class SignInUseCaseImpl @Inject constructor(
    private val useCase: CoroutinesUseCase,
    private val repository: AuthenticationRepository,
) : SignInUseCase, UseCase<SignInParameter, AuthenticationType>() {
    override suspend fun doWork(parameter: SignInParameter): AuthenticationType =
        withContext(useCase.io()) { repository.signIn(parameter) }
}
