package com.bed.core.entities.value

import arrow.core.right
import arrow.core.leftNel
import arrow.core.EitherNel

@JvmInline
value class NameValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        private const val PATTERN = "\\b[A-Za-zÀ-ú][A-Za-zÀ-ú]+,?\\s[A-Za-zÀ-ú][A-Za-zÀ-ú]{2,19}\\b"
        operator fun invoke(value: String): EitherNel<String, NameValue> =
            if(isValid(value)) NameValue(value).right()
            else Values.INVALID_FULL_NAME.message.leftNel()

        private fun isValid(value: String) = PATTERN.toRegex().matches(value)
    }
}
