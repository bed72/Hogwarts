package com.bed.core.domain.parameters.authentication

import com.bed.core.values.Code
import com.bed.core.values.Password

import com.bed.core.domain.parameters.Parameter

data class ResetParameter(
    val code: Code = Code(""),
    val password: Password = Password(""),
) : Parameter {
    override fun hasMessages(): MutableSet<String?> =
        mutableSetOf(code.message, password.message).apply { removeIf { it == null } }
}
