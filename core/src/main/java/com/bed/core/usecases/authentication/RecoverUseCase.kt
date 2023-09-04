package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.core.domain.parameters.authentication.RecoverParameter

interface RecoverUseCase {
    operator fun invoke(parameter: RecoverParameter): Flow<Boolean>
}

class RecoverUseCaseImpl @Inject constructor(
    private val useCase: CoroutinesUseCase,
    private val repository: AuthenticationRepository,
) : RecoverUseCase, UseCase<RecoverParameter, Boolean>() {
    override suspend fun doWork(parameter: RecoverParameter): Boolean =
        withContext(useCase.io()) { repository.recover(parameter) }
}
