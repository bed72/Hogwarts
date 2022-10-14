package com.bed.seller.presentation.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.distinctUntilChanged

import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.auth.AuthUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.presentation.utils.TripleMediatorLiveData

import com.bed.seller.presentation.ui.auth.signup.states.SignUpLiveData
import com.bed.seller.presentation.ui.auth.signup.states.validators.NameValidatorLiveData

import com.bed.seller.presentation.ui.auth.commons.Auth
import com.bed.seller.presentation.ui.auth.commons.states.validators.EmailValidatorLiveData
import com.bed.seller.presentation.ui.auth.commons.states.validators.PasswordValidatorLiveData

class SignUpViewModel(
    authCommons: Auth,
    authUseCase: AuthUseCase,
    validatorUseCase: ValidatorUseCase,
    coroutineDispatcher: CoroutinesDispatchers
) : ViewModel() {

    val auth = SignUpLiveData(
        authCommons,
        authUseCase,
        coroutineDispatcher
    )

    val name = NameValidatorLiveData(validatorUseCase)
    val email = EmailValidatorLiveData(validatorUseCase)
    val password = PasswordValidatorLiveData(validatorUseCase)

    val formIsValid: LiveData<Boolean>
        get() = TripleMediatorLiveData(name.isValid, email.isValid, password.isValid)
            .distinctUntilChanged()
            .switchMap { state ->
                val firstValue = state.first?.valid ?: false
                val secondValue = state.second?.valid ?: false
                val thirdValue = state.third?.valid ?: false

                return@switchMap liveData {
                    if (firstValue and secondValue and thirdValue)
                        emit(firstValue and secondValue and thirdValue)
                }
            }

    fun submit(data: AuthBodyRequestEntity) {
        auth.signUp(data)
    }
}

