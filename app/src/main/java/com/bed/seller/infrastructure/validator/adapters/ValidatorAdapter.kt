package com.bed.seller.infrastructure.validator.adapters

import java.util.regex.Pattern

import com.bed.seller.data.client.ValidatorClient

import com.bed.seller.domain.entities.form.TextFieldEntity
import com.bed.seller.domain.entities.form.ValidateFormEntity

class ValidatorAdapter : ValidatorClient {

    override fun validateName(name: String): ValidateFormEntity =
        if (nameValidate(name).not()) ValidateFormEntity("",false, TextFieldEntity.NAME)
        else ValidateFormEntity(name,true, TextFieldEntity.NAME)

    override fun validateEmail(email: String): ValidateFormEntity =
        if (emailValidate(email).not()) ValidateFormEntity("",false, TextFieldEntity.EMAIL)
        else ValidateFormEntity(email,true, TextFieldEntity.EMAIL)

    override fun validatePassword(password: String): ValidateFormEntity =
        if (passwordValidate(password).not()) ValidateFormEntity("",false, TextFieldEntity.PASSWORD)
        else ValidateFormEntity(password, true, TextFieldEntity.PASSWORD)

    private fun nameValidate(password: String): Boolean =
        Pattern.compile(
            "[a-zA-Z]{2,}"
        ).matcher(password).matches()

    private fun emailValidate(email: String): Boolean =
        Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        ).matcher(email).matches()

    private fun passwordValidate(password: String): Boolean =
        Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~\$^+=<>]).{8,20}\$"
        ).matcher(password).matches()
}
