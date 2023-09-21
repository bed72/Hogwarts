package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class DescriptionValue(val value: String) : ValueObject<DescriptionValue> {
    override fun validate(): Either<String, DescriptionValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> {
        val pattern = "^[A-zÀ-ú '´.,!]+".toRegex()

        return when {
            value.length >= 64 -> false to Values.INVALID_DESCRIPTION_BIG.value
            value.length <= 2 -> false to Values.INVALID_DESCRIPTION_SMALL.value
            pattern.matches(value).not() -> false to Values.INVALID_DESCRIPTION.value
            else -> true to value
        }
    }
}
