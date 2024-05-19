package com.bed.core.usecases.authentication

import javax.inject.Inject

import kotlinx.coroutines.flow.flowOn

import com.bed.core.alias.AuthenticationCoreType

import com.bed.core.entities.input.AuthenticationInput

import com.bed.core.repositories.CoroutinesRepository
import com.bed.core.repositories.AuthenticationRepository

interface SignInUsecase {
    suspend operator fun invoke(parameter: AuthenticationInput): AuthenticationCoreType
}

class SignInUsecaseImpl @Inject constructor(
    private val coroutinesRepository: CoroutinesRepository,
    private val authenticationRepository: AuthenticationRepository,
) : SignInUsecase {
    override suspend fun invoke(parameter: AuthenticationInput): AuthenticationCoreType =
        authenticationRepository.signIn(parameter).flowOn(coroutinesRepository.io())
}
