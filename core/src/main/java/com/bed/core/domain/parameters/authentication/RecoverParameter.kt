package com.bed.core.domain.parameters.authentication

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import com.bed.core.values.Email

import com.bed.core.domain.parameters.Parameter

data class RecoverParameter(
    val email: Email = Email(""),
) : Parameter<RecoverParameter> {
    override fun isValid(): Either<MutableSet<String?>, RecoverParameter> =
        if (email.isValid) this.right() else mutableSetOf(email.message).left()
}
