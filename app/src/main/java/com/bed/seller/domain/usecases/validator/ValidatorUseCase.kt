package com.bed.seller.domain.usecases.validator

import com.bed.seller.domain.entities.form.ValidateFormEntity

interface ValidatorUseCase {
    fun validateName(params: String): ValidateFormEntity
    fun validateEmail(params: String): ValidateFormEntity
    fun validatePassword(params: String): ValidateFormEntity
}
