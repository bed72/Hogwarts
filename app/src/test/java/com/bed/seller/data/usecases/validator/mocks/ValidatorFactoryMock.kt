package com.bed.seller.data.usecases.validator.mocks

import com.bed.seller.data.usecases.mocks.CommonMock
import com.bed.seller.domain.entities.form.TextFieldEntity
import com.bed.seller.domain.entities.form.ValidateFormEntity

class ValidatorFactoryMock {
    private val invalidInputs = ValidateFormEntity()

    val nameIsValid = validateInputs(IsValid.Name)
    val emailIsValid = validateInputs(IsValid.Email)
    val passwordIsValid = validateInputs(IsValid.Password)

    sealed class IsValid {
        object Name : IsValid()
        object Email : IsValid()
        object  Password : IsValid()
    }

    private fun validateInputs(input: IsValid) =
        when (input) {
            IsValid.Name -> ValidateFormEntity(
                PARAMS_NAME,
                true,
                TextFieldEntity.NAME
            )
            IsValid.Email -> ValidateFormEntity(
                CommonMock.PARAMS_EMAIL,
                true,
                TextFieldEntity.EMAIL
            )
            IsValid.Password -> ValidateFormEntity(
                CommonMock.PARAMS_PASSWORD,
                true,
                TextFieldEntity.PASSWORD
            )
        }

    companion object {
        const val PARAMS_NAME = "Bed"
    }
}
