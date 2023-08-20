package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class EmailValue(val value: String) : ValueObject<EmailValue> {

    override fun validate(): Either<String, EmailValue> {
        val (isValid, message) = rule(value)

        return if (isValid) this.right() else message.left()
    }

    private fun rule(value: String): Pair<Boolean, String> {
        val pattern = "^[a-zA-Z\\d+_.-]+@[a-zA-Z\\d.-]+\\.[a-zA-z]{2,3}\$".toRegex()

        return when {
            pattern.matches(value).not() -> false to "Preencha um e-mail válido."
            else -> true to value
        }
    }
}
