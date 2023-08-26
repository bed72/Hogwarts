package com.bed.core.domain.parameters.authentication

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate

import com.bed.core.values.EmailValue
import com.bed.core.values.PasswordValue

import com.bed.core.domain.parameters.Parameter

data class SignInParameter(
    val email: EmailValue,
    val password: PasswordValue,
) : Parameter<SignInParameter>() {
    override fun isValid(): Either<List<String>, SignInParameter> = either {
        val params = email.validate() to password.validate()

        zipOrAccumulate(
            { before, after -> combine(before, after) },
            { ensure(params.first.isRight()) { prepare(params.first.leftOrNull()) } },
            { ensure(params.second.isRight()) { prepare(params.second.leftOrNull()) } },
        ) { _, _ -> SignInParameter( email, password) }
    }

    companion object {
        operator fun invoke() =
            SignInParameter(EmailValue(""), PasswordValue(""))
    }
}
