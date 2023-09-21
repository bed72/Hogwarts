package com.bed.core.values

import arrow.core.Either

sealed interface ValueObject<out T> {
    fun validate(): Either<String, T>
}

enum class Values(val value: String) {
    INVALID_DATE("Preencha uma data válida."),
    INVALID_VALIDATED_AT("A data precisa ser a partir de amanhã."),
    INVALID_CODE("O código não é válido."),
    INVALID_EMAIL("Preencha um e-mail válido."),
    INVALID_DESCRIPTION("Preencha uma descrição válida."),
    INVALID_DESCRIPTION_BIG("A descrição precisam ser menor que 64 caracteres."),
    INVALID_DESCRIPTION_SMALL("A descrição precisam ser maior que 2 caracteres."),
    INVALID_USER_NAME("Preencha um nome e sobrenome válidos."),
    INVALID_USER_NAME_BIG("O nome e sobrenome precisam ser menor que 32 caracteres."),
    INVALID_USER_NAME_SMALL("O nome e sobrenome precisam ser maior que 2 caracteres."),
    INVALID_PRODUCT_NAME("Preencha um nome de produto válido."),
    INVALID_PRODUCT_NAME_BIG("O nome do produto precisa ser menor que 16 caracteres."),
    INVALID_PRODUCT_NAME_SMALL("O nome do produto precisa ser maior que 2 caracteres."),
    INVALID_PRICE("Preencha um valor valído."),
    INVALID_PRICE_NEEDS_BIGGER_ZERO("Preencha um valor maior que R\$ 0."),
    INVALID_PASSWORD("Preencha uma senha válida."),
    INVALID_PASSWORD_NOT_EQUALS("As senhas precisam ser iguais."),
    INVALID_PASSWORD_MIN_SIZE("A senha presica conter mais de 5 caracteres."),
    INVALID_PASSWORD_NEEDS_NUMBER("A senha presica conter caracteres numéricos."),
    INVALID_PASSWORD_NEEDS_UPPER_CASE_LETTERS("A senha presica conter caracteres maiúsculos."),
}
