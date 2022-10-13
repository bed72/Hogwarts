package com.bed.seller.domain.entities.form

data class ValidateFormEntity(
    val value: String = "",
    val valid: Boolean = false,
    val textField: TextFieldEntity = TextFieldEntity.NONE
)
