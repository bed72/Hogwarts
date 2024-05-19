package com.bed.core.entities.input

import arrow.core.EitherNel

import com.bed.core.entities.value.validate
import com.bed.core.entities.value.EmailValue

data class RecoverInput(
    val email: EmailValue
) {
    companion object {
        operator fun invoke(email: String): EitherNel<String, RecoverInput> =
            validate(EmailValue(email)) { RecoverInput(it) }
    }
}
