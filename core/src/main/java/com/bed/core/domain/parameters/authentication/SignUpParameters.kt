package com.bed.core.domain.parameters.authentication

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate

import com.bed.core.values.NameValue
import com.bed.core.values.EmailValue
import com.bed.core.values.PasswordValue

import com.bed.core.domain.parameters.Parameters

data class SignUpParameters(
    val name: NameValue,
    val email: EmailValue,
    val password: PasswordValue,
) : Parameters<SignUpParameters>() {
    override fun isValid(): Either<List<String>, SignUpParameters> = either {
        val params = Triple(name(), email(), password())

        zipOrAccumulate(
            { before, after -> combine(before, after) },
            { ensure(params.first.isRight()) { prepare(params.first.leftOrNull()) } },
            { ensure(params.second.isRight()) { prepare(params.second.leftOrNull()) } },
            { ensure(params.third.isRight()) { prepare(params.third.leftOrNull()) } },
        ) { _, _, _ -> SignUpParameters(name, email, password) }
    }
}