package com.bed.seller.data.client

import com.bed.seller.domain.entities.form.ValidateFormEntity

interface ValidatorClient {
    fun validateName(name: String): ValidateFormEntity
    fun validateEmail(email: String): ValidateFormEntity
    fun validatePassword(password: String): ValidateFormEntity
}
