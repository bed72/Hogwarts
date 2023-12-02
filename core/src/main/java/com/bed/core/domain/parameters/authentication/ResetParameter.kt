package com.bed.core.domain.parameters.authentication

import arrow.core.EitherNel

import com.bed.core.values.validate
import com.bed.core.values.CodeValue
import com.bed.core.values.MessageValue
import com.bed.core.values.PasswordValue

data class ResetParameter(
    val code: CodeValue,
    val password: PasswordValue
)  {
    companion object {
        operator fun invoke(code: String, password: String): EitherNel<MessageValue, ResetParameter> =
            validate(CodeValue(code), PasswordValue(password)) { validCode, validPassword ->
                ResetParameter(validCode, validPassword)
            }
    }
}
