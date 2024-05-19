package com.bed.core.entities.input

import arrow.core.EitherNel

import com.bed.core.entities.value.CodeValue
import com.bed.core.entities.value.PasswordValue
import com.bed.core.entities.value.accumulatedValidation

data class ResetInput(
    val code: CodeValue,
    val password: PasswordValue
)  {
    companion object {
        operator fun invoke(code: String, password: String): EitherNel<String, ResetInput> =
            accumulatedValidation(CodeValue(code), PasswordValue(password)) { validCode, validPassword ->
                ResetInput(validCode, validPassword)
            }
    }
}
