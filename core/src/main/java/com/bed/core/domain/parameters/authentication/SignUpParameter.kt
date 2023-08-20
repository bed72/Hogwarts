package com.bed.core.domain.parameters.authentication

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate

import com.bed.core.values.NameValue
import com.bed.core.values.EmailValue
import com.bed.core.values.PasswordValue

import com.bed.core.domain.parameters.Parameter

data class SignUpParameter(
    val name: NameValue,
    val email: EmailValue,
    val password: PasswordValue,
) : Parameter<SignUpParameter>() {
    override fun isValid(): Either<List<String>, SignUpParameter> = either {
        val params = Triple(name.validate(), email.validate(), password.validate())

        zipOrAccumulate(
            { before, after -> combine(before, after) },
            { ensure(params.first.isRight()) { prepare(params.first.leftOrNull()) } },
            { ensure(params.second.isRight()) { prepare(params.second.leftOrNull()) } },
            { ensure(params.third.isRight()) { prepare(params.third.leftOrNull()) } },
        ) { _, _, _ -> SignUpParameter(name, email, password) }
    }

    companion object {
        operator fun invoke() =
            SignUpParameter(NameValue(""), EmailValue(""), PasswordValue(""))
    }
}
