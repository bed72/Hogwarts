package com.bed.seller.data.usecases.validator

import com.bed.seller.data.client.ValidatorClient

import com.bed.seller.domain.entities.form.ValidateFormEntity
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

class RemoteValidatorUseCase(private val validatorClient: ValidatorClient) : ValidatorUseCase {
    override fun validateName(params: String): ValidateFormEntity = validatorClient.validateName(params)
    override fun validateEmail(params: String): ValidateFormEntity = validatorClient.validateEmail(params)
    override fun validatePassword(params: String): ValidateFormEntity = validatorClient.validatePassword(params)
}
