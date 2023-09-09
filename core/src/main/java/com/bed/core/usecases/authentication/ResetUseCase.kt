package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.data.repositories.AuthenticationRepository
import com.bed.core.domain.parameters.authentication.ResetParameter

interface ResetUseCase {
    operator fun invoke(parameter: ResetParameter): Flow<Boolean>
}

class ResetUseCaseImpl @Inject constructor(
    private val useCase: CoroutinesUseCase,
    private val repository: AuthenticationRepository
) : ResetUseCase, UseCase<ResetParameter, Boolean>() {
    override suspend fun doWork(parameter: ResetParameter): Boolean =
        withContext(useCase.io()) { repository.reset(parameter) }
}
