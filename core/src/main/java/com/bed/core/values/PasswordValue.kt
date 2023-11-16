package com.bed.core.values

import arrow.core.Nel
import arrow.core.right
import arrow.core.Either
import arrow.core.leftNel

@JvmInline
value class PasswordValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        operator fun invoke(value: String?): Either<Nel<MessageValue>, PasswordValue> =
            when {
                value == null -> MessageValue("O senha não pode ser nula.").leftNel()
                isValid(value) -> PasswordValue(value).right()
                value.length <= 6 -> MessageValue("A senha presica conter mais de 6 caracteres.").leftNel()
                value.contains(".*\\d.*".toRegex()).not() -> MessageValue("A senha presica conter caracteres numéricos.").leftNel()
                value.contains(".*[A-Z].*".toRegex()).not() -> MessageValue("A senha presica conter caracteres maiúsculos.").leftNel()
                else -> MessageValue("Preencha uma senha válida.").leftNel()
            }

        private fun isValid(value: String): Boolean {
            val pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}\$".toRegex()

            return pattern.matches(value)
        }
    }
}
