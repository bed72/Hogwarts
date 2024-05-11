package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase

import com.bed.core.data.repositories.CoroutinesRepository
import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.core.domain.parameters.authentication.ResetParameter

interface ResetUsecase {
    operator fun invoke(parameter: ResetParameter): Flow<Boolean>
}

class ResetUsecaseImpl @Inject constructor(
    private val coroutinesRepository: CoroutinesRepository,
    private val authenticationRepository: AuthenticationRepository,
) : ResetUsecase, UseCase<ResetParameter, Boolean>() {
    override suspend fun doWork(parameter: ResetParameter): Boolean =
        withContext(coroutinesRepository.io()) { authenticationRepository.reset(parameter) }
}
