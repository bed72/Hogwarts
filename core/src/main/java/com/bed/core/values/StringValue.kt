package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class StringValue(val value: String) : ValueObject<StringValue> {

    override fun validate(): Either<String, StringValue> {
        val (isValid, message) = rule(value)

        return if (isValid) this.right() else message.left()
    }

    private fun rule(value: String): Pair<Boolean, String> {

        return when {
            value.isBlank() -> false to "Preencha um valor válido."
            else -> true to value
        }
    }
}
