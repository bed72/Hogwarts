package com.bed.core.values

import arrow.core.Nel
import arrow.core.right
import arrow.core.Either
import arrow.core.leftNel

@JvmInline
value class CodeValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        operator fun invoke(value: String?): Either<Nel<MessageValue>, CodeValue> =
            when {
                value == null -> MessageValue("O código não podem ser nulo.").leftNel()
                isValid(value) -> CodeValue(value).right()
                else -> MessageValue("Preencha um código válido.").leftNel()
            }

        private fun isValid(value: String): Boolean = value.isNotBlank()
    }
}
