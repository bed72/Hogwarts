package com.bed.core.entities.value

import arrow.core.right
import arrow.core.leftNel
import arrow.core.EitherNel

@JvmInline
value class EmailValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        private const val PATTERN = "^[a-zA-Z\\d+_.-]+@[a-zA-Z\\d.-]+\\.[a-zA-z]{2,3}\$"
        operator fun invoke(value: String): EitherNel<String, EmailValue> =
            if(isValid(value)) EmailValue(value).right()
            else Values.INVALID_EMAIL.message.leftNel()

        private fun isValid(value: String) = PATTERN.toRegex().matches(value)
    }
}
