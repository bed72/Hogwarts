package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class EmailValue(val value: String) : ValueObject<EmailValue> {

    override fun validate(): Either<String, EmailValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> {
        val pattern = "^[a-zA-Z\\d+_.-]+@[a-zA-Z\\d.-]+\\.[a-zA-z]{2,3}\$".toRegex()

        return when {
            pattern.matches(value).not() -> false to Values.INVALID_EMAIL.value
            else -> true to value
        }
    }
}
