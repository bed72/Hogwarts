package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCaseWithoutParameter
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.data.repositories.AuthenticationRepository

interface VerifyUseCase {
    operator fun invoke(): Flow<Boolean>
}

class VerifyUseCaseImpl @Inject constructor(
    private val useCase: CoroutinesUseCase,
    private val repository: AuthenticationRepository,
) : VerifyUseCase, UseCaseWithoutParameter<Boolean>() {
    override suspend fun doWork(): Boolean =
        withContext(useCase.io()) { repository.verify() }
}
