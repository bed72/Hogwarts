package com.bed.core.domain.parameters.authentication

import arrow.core.EitherNel

import com.bed.core.values.validate
import com.bed.core.values.EmailValue
import com.bed.core.values.MessageValue
import com.bed.core.values.PasswordValue

data class AuthenticationParameter(
    val email: EmailValue,
    val password: PasswordValue,
) {
    companion object {
        operator fun invoke(email: String, password: String): EitherNel<MessageValue, AuthenticationParameter> =
            validate(EmailValue(email), PasswordValue(password)) { validEmail, validPassword ->
                AuthenticationParameter(validEmail, validPassword)
            }
    }
}
