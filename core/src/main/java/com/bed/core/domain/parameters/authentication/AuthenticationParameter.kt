package com.bed.core.domain.parameters.authentication

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate

import com.bed.core.values.EmailValue
import com.bed.core.values.PasswordValue

import com.bed.core.domain.parameters.Parameter

data class AuthenticationParameter(
    val email: EmailValue,
    val password: PasswordValue,
) : Parameter<AuthenticationParameter>() {
    override fun isValid(): Either<List<String>, AuthenticationParameter> = either {
        val parameter = email.validate() to password.validate()

        zipOrAccumulate(
            { before, after -> combine(before, after) },
            { ensure(parameter.first.isRight()) { prepare(parameter.first.leftOrNull()) } },
            { ensure(parameter.second.isRight()) { prepare(parameter.second.leftOrNull()) } },
        ) { _, _ -> AuthenticationParameter(email, password) }
    }

    companion object {
        operator fun invoke() =
            AuthenticationParameter(EmailValue(""), PasswordValue(""))
    }
}
