package com.bed.core.values

import arrow.core.Nel
import arrow.core.right
import arrow.core.Either
import arrow.core.leftNel

@JvmInline
value class NameValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        operator fun invoke(value: String?): Either<Nel<MessageValue>, NameValue> =
            when {
                value == null -> MessageValue("O nome e sobrenome não podem ser nulos.").leftNel()
                isValid(value) -> NameValue(value).right()
                else -> MessageValue("Preencha um nome e sobrenome válidos.").leftNel()
            }

        private fun isValid(value: String): Boolean {
            val pattern = "\\b[A-Za-zÀ-ú][A-Za-zÀ-ú]+,?\\s[A-Za-zÀ-ú][A-Za-zÀ-ú]{2,19}\\b".toRegex()

            return pattern.matches(value)
        }
    }
}
