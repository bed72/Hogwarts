package com.bed.core.values

import arrow.core.Nel
import arrow.core.right
import arrow.core.Either
import arrow.core.leftNel

@JvmInline
value class EmailValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        operator fun invoke(value: String?): Either<Nel<MessageValue>, EmailValue> =
            when {
                value == null -> MessageValue("O e-mail não pode ser nulo.").leftNel()
                isValid(value) -> EmailValue(value).right()
                else -> MessageValue("Preencha um e-mail válido.").leftNel()
            }

        private fun isValid(value: String): Boolean {
            val pattern = "^[a-zA-Z\\d+_.-]+@[a-zA-Z\\d.-]+\\.[a-zA-z]{2,3}\$".toRegex()

            return pattern.matches(value)
        }
    }
}
