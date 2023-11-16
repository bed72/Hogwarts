package com.bed.core.domain.parameters.authentication

import arrow.core.EitherNel

import com.bed.core.values.validate
import com.bed.core.values.NameValue
import com.bed.core.values.EmailValue
import com.bed.core.values.MessageValue
import com.bed.core.values.PasswordValue

data class SignUpParameter(
    val name: NameValue,
    val email: EmailValue,
    val password: PasswordValue,
) {
    companion object {
        operator fun invoke(name: String, email: String, password: String): EitherNel<MessageValue, SignUpParameter> =
            validate(
                NameValue(name),
                EmailValue(email),
                PasswordValue(password)
            ) { validName, validEmail, validPassword ->
                SignUpParameter(validName, validEmail, validPassword)
            }
    }
}
