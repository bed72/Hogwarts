package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class NameValue(val value: String) : ValueObject<NameValue> {

    override fun validate(): Either<String, NameValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> {
        val pattern = "\\b[A-Za-zÀ-ú][A-Za-zÀ-ú]+,?\\s[A-Za-zÀ-ú][A-Za-zÀ-ú]{2,19}\\b".toRegex()

        return when {
            pattern.matches(value).not() -> false to Values.INVALID_NAME.value
            else -> true to value
        }
    }
}
