package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class StringValue(val value: String) : ValueObject<StringValue> {

    override fun validate(): Either<String, StringValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> =
        when {
            value.isBlank() -> false to Values.INVALID_VALUE.value
            else -> true to value
        }
}
