package com.bed.core.domain.parameters.authentication

import com.bed.core.values.Email
import com.bed.core.values.Password

import com.bed.core.domain.parameters.Parameter

data class AuthenticationParameter(
    val email: Email = Email(""),
    val password: Password = Password(""),
) : Parameter {
    override fun hasMessages(): MutableSet<String?> =
        mutableSetOf(email.message, password.message).apply { removeIf { it == null } }
}
