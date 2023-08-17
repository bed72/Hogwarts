package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.core.domain.alias.SignUpType
import com.bed.core.domain.parameters.authentication.SignUpParameters

interface SignUpUseCase {
    operator fun invoke(parameters: SignUpParameters): Flow<SignUpType>
}

class SignUpUseCaseImpl @Inject constructor(
    private val useCase: CoroutinesUseCase,
    private val repository: AuthenticationRepository,
) : SignUpUseCase, UseCase<SignUpParameters, SignUpType>() {
    override suspend fun doWork(parameters: SignUpParameters): SignUpType =
        withContext(useCase.io()) { repository.signUp(parameters) }
}
