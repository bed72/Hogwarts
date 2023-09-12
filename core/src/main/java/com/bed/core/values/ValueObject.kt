package com.bed.core.values

import arrow.core.Either

sealed interface ValueObject<out T> {
    fun validate(): Either<String, T>
}

enum class Values(val value: String) {
    INVALID_VALUE("Preencha um valor válido."),
    INVALID_EMAIL("Preencha um e-mail válido."),
    INVALID_NAME("Preencha um nome e sobrenome válidos."),
    INVALID_PASSWORD("Preencha uma senha válida."),
    INVALID_PASSWORD_NOT_EQUALS("As senhas precisam ser iguais."),
    INVALID_PASSWORD_MIN_SIZE("A senha presica conter mais de 5 caracteres."),
    INVALID_PASSWORD_NEEDS_NUMBER("A senha presica conter caracteres numéricos."),
    INVALID_PASSWORD_NEEDS_UPPER_CASE_LETTERS("A senha presica conter caracteres maiúsculos."),
}
