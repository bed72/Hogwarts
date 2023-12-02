package com.bed.core.usecases.authentication

import javax.inject.Inject

import com.bed.core.data.repositories.AuthenticationRepository

interface SignOutUseCase {
    operator fun invoke()
}

class SignOutUseCaseImpl @Inject constructor(
    private val repository: AuthenticationRepository,
) : SignOutUseCase {
    override fun invoke(): Unit = repository.signOut()
}
