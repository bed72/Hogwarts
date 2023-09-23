package com.bed.core.domain.parameters.authentication

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import com.bed.core.values.Code
import com.bed.core.values.Password

import com.bed.core.domain.parameters.Parameter

data class ResetParameter(
    val code: Code = Code(""),
    val password: Password = Password(""),
) : Parameter<ResetParameter> {
    override fun isValid(): Either<MutableSet<String?>, ResetParameter> {
        val data = mutableSetOf(code.message, password.message).apply { removeIf { it == null } }

        return if (data.all { it == null }) this.right() else data.left()
    }
}
