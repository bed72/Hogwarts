package com.bed.core.domain.parameters.authentication

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import com.bed.core.values.Email
import com.bed.core.values.Password

import com.bed.core.domain.parameters.Parameter

data class AuthenticationParameter(
    val email: Email = Email(""),
    val password: Password = Password(""),
) : Parameter<AuthenticationParameter> {
    override fun isValid(): Either<MutableSet<String?>, AuthenticationParameter> {
        val data = mutableSetOf(email.message, password.message).apply { removeIf { it == null } }

        return if (data.all { it == null }) this.right() else data.left()
    }
}
