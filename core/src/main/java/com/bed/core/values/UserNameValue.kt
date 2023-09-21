package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class UserNameValue(val value: String) : ValueObject<UserNameValue> {

    override fun validate(): Either<String, UserNameValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> {
        val pattern = "\\b[A-Za-zÀ-ú][A-Za-zÀ-ú]+,?\\s[A-Za-zÀ-ú][A-Za-zÀ-ú]{2,31}\\b".toRegex()

        return when {
            value.length >= 31 -> false to Values.INVALID_USER_NAME_BIG.value
            value.length <= 2 -> false to Values.INVALID_USER_NAME_SMALL.value
            pattern.matches(value).not() -> false to Values.INVALID_USER_NAME.value
            else -> true to value
        }
    }
}
