package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase

import com.bed.core.data.repositories.CoroutinesRepository
import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.core.domain.parameters.authentication.RecoverParameter

interface RecoverUsecase {
    operator fun invoke(parameter: RecoverParameter): Flow<Boolean>
}

class RecoverUsecaseImpl @Inject constructor(
    private val coroutinesRepository: CoroutinesRepository,
    private val authenticationRepository: AuthenticationRepository,
) : RecoverUsecase, UseCase<RecoverParameter, Boolean>() {
    override suspend fun doWork(parameter: RecoverParameter): Boolean =
        withContext(coroutinesRepository.io()) { authenticationRepository.recover(parameter) }
}
