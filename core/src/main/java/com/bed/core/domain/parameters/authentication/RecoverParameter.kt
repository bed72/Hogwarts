package com.bed.core.domain.parameters.authentication

import arrow.core.Either

import com.bed.core.values.EmailValue
import com.bed.core.domain.parameters.Parameter

data class RecoverParameter(
    val email: EmailValue,
) : Parameter<RecoverParameter>() {
    override fun isValid(): Either<List<String>, RecoverParameter> =
        email.validate()
            .map { success -> RecoverParameter(success) }
            .mapLeft { failure -> listOf(failure) }

    companion object {
        operator fun  invoke() = RecoverParameter(EmailValue(""))
    }
}
