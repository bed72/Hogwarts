package com.bed.core.entities.input

import arrow.core.EitherNel

import com.bed.core.entities.value.EmailValue
import com.bed.core.entities.value.PasswordValue
import com.bed.core.entities.value.accumulatedValidation

data class AuthenticationInput(
    val email: EmailValue,
    val password: PasswordValue,
) {
    companion object {
        operator fun invoke(email: String, password: String): EitherNel<String, AuthenticationInput> =
            accumulatedValidation(EmailValue(email), PasswordValue(password)) { validEmail, validPassword ->
                AuthenticationInput(validEmail, validPassword)
            }
    }
}
