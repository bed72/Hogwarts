package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.flowOn

import com.bed.core.alias.AuthenticationCoreType

import com.bed.core.entities.input.AuthenticationInput

import com.bed.core.repositories.CoroutinesRepository
import com.bed.core.repositories.AuthenticationRepository

interface SignUpUsecase {
    suspend operator fun invoke(parameter: AuthenticationInput): AuthenticationCoreType
}

class SignUpUsecaseImpl @Inject constructor(
    private val coroutinesRepository: CoroutinesRepository,
    private val authenticationRepository: AuthenticationRepository,
) : SignUpUsecase {
    override suspend fun invoke(parameter: AuthenticationInput): AuthenticationCoreType =
        authenticationRepository.signUp(parameter).flowOn(coroutinesRepository.io())
}
