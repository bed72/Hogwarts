package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class PasswordValue (val value: String) : ValueObject {

    override operator fun invoke(): Either<String, String> {
        val (isValid, message) = rule(value)

        return if (isValid) value.right() else message.left()
    }

    private fun rule(value: String): Pair<Boolean, String> {
        val patternNeedsNumberCharacter = ".*\\d.*".toRegex()
        val patternNeedsUpperCaseCharacter = ".*[A-Z].*".toRegex()

        return when {
            value.isEmpty() -> false to "Preencha sua senha."
            patternNeedsNumberCharacter.matches(value).not() ->
                false to "A senha presica conter caracteres numéricos."
            patternNeedsUpperCaseCharacter.matches(value).not() ->
                false to "A senha presica conter caracteres maiúsculos."
            else -> true to value
        }
    }
}
