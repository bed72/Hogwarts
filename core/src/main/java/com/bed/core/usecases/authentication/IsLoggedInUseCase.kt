package com.bed.core.usecases.authentication

import javax.inject.Inject

import com.bed.core.data.repositories.AuthenticationRepository

interface IsLoggedInUseCase {
    operator fun invoke(): Boolean
}

class IsLoggedInUseCaseImpl @Inject constructor(
    private val repository: AuthenticationRepository,
) : IsLoggedInUseCase {
    override fun invoke(): Boolean = repository.isLoggedIn()
}
