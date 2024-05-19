package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

import com.bed.core.entities.input.ResetInput

import com.bed.core.repositories.CoroutinesRepository
import com.bed.core.repositories.AuthenticationRepository

interface ResetUsecase {
    suspend operator fun invoke(parameter: ResetInput): Flow<Boolean>
}

class ResetUsecaseImpl @Inject constructor(
    private val coroutinesRepository: CoroutinesRepository,
    private val authenticationRepository: AuthenticationRepository,
) : ResetUsecase {
    override suspend fun invoke(parameter: ResetInput): Flow<Boolean> =
        authenticationRepository.reset(parameter).flowOn(coroutinesRepository.io())
}
