package com.bed.core.usecases.authentication

import javax.inject.Inject

import com.bed.core.repositories.AuthenticationRepository

interface SignOutUsecase {
    operator fun invoke()
}

class SignOutUsecaseImpl @Inject constructor(
    private val repository: AuthenticationRepository
) : SignOutUsecase {
    override fun invoke(): Unit = repository.signOut()
}
