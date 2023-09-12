package com.bed.core.domain.parameters.authentication

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate

import com.bed.core.values.Values
import com.bed.core.values.StringValue
import com.bed.core.values.PasswordValue

import com.bed.core.domain.parameters.Parameter

data class ResetParameter(
    val code: StringValue,
    val password: PasswordValue,
    val repeatPassword: PasswordValue
) : Parameter<ResetParameter>() {
    override fun isValid(): Either<List<String>, ResetParameter> = either {
        val parameter = Triple(code.validate(), password.validate(), repeatPassword.validate())

        zipOrAccumulate(
            { before, after -> combine(before, after) },
            { ensure(parameter.first.isRight()) { prepare(parameter.first.leftOrNull()) } },
            { ensure(parameter.second.isRight()) { prepare(parameter.second.leftOrNull()) } },
            { ensure(parameter.third.isRight()) { prepare(parameter.third.leftOrNull()) } },
            {
                ensure(password.value == repeatPassword.value) {
                    prepare(Values.INVALID_PASSWORD_NOT_EQUALS.value)
                }
            }
        ) { _, _, _, _ -> ResetParameter(code, password, repeatPassword) }
    }

    companion object {
        operator fun invoke() =
            ResetParameter(StringValue(""), PasswordValue(""), PasswordValue(""))
    }
}
