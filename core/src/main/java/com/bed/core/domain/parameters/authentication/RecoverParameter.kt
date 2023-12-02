package com.bed.core.domain.parameters.authentication

import arrow.core.EitherNel

import com.bed.core.values.validate
import com.bed.core.values.EmailValue
import com.bed.core.values.MessageValue

data class RecoverParameter(
    val email: EmailValue
) {
    companion object {
        operator fun invoke(email: String): EitherNel<MessageValue, RecoverParameter> =
            validate(EmailValue(email)) { RecoverParameter(it) }
    }
}
