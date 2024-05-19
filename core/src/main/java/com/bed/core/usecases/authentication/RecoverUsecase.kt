package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

import com.bed.core.entities.input.RecoverInput

import com.bed.core.repositories.CoroutinesRepository
import com.bed.core.repositories.AuthenticationRepository

interface RecoverUsecase {
    suspend operator fun invoke(parameter: RecoverInput): Flow<Boolean>
}

class RecoverUsecaseImpl @Inject constructor(
    private val coroutinesRepository: CoroutinesRepository,
    private val authenticationRepository: AuthenticationRepository,
) : RecoverUsecase {
    override suspend fun invoke(parameter: RecoverInput): Flow<Boolean> =
        authenticationRepository.recover(parameter).flowOn(coroutinesRepository.io())
}
