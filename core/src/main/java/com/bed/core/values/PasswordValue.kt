package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class PasswordValue (val value: String) : ValueObject<PasswordValue> {

    override fun validate(): Either<String, PasswordValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> {
        val minSize = 6
        val patternNeedsNumberCharacter = ".*\\d.*".toRegex()
        val patternNeedsUpperCaseCharacter = ".*[A-Z].*".toRegex()

        return when {
            value.isEmpty() -> false to Values.INVALID_PASSWORD.value
            value.length <= minSize ->
                false to Values.INVALID_PASSWORD_MIN_SIZE.value
            patternNeedsNumberCharacter.matches(value).not() ->
                false to Values.INVALID_PASSWORD_NEEDS_NUMBER.value
            patternNeedsUpperCaseCharacter.matches(value).not() ->
                false to Values.INVALID_PASSWORD_NEEDS_UPPER_CASE_LETTERS.value
            else -> true to value
        }
    }
}
