package com.bed.core.usecases.authentication

import javax.inject.Inject

import com.bed.core.repositories.AuthenticationRepository

interface IsLoggedInUsecase {
    operator fun invoke(): Boolean
}

class IsLoggedInUsecaseImpl @Inject constructor(
    private val repository: AuthenticationRepository,
) : IsLoggedInUsecase {
    override fun invoke(): Boolean = repository.isLoggedIn()
}
