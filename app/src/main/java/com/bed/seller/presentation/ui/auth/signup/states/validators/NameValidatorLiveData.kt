package com.bed.seller.presentation.ui.auth.signup.states.validators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.bed.seller.domain.entities.form.ValidateFormEntity
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

class NameValidatorLiveData(private val validatorUseCase: ValidatorUseCase) {
    private val _isValid = MutableLiveData<ValidateFormEntity>()
    val isValid: LiveData<ValidateFormEntity> get() = _isValid

    fun set(value: String) {
        if (value.isNotEmpty()) _isValid.value = validatorUseCase.validateName(value)
    }
}
