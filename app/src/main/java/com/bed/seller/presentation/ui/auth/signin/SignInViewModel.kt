package com.bed.seller.presentation.ui.auth.signin

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.distinctUntilChanged

import com.bed.seller.domain.usecases.auth.SignInUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers
import com.bed.seller.domain.entities.auth.signin.SignInBodyRequestEntity

import com.bed.seller.presentation.utils.PairMediatorLiveData

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.auth.signin.states.SignInLiveData
import com.bed.seller.presentation.ui.auth.commons.states.validators.EmailValidatorLiveData
import com.bed.seller.presentation.ui.auth.commons.states.validators.PasswordValidatorLiveData

class SignInViewModel(
    commons: Commons,
    validatorUseCase: ValidatorUseCase,
    authSignInUseCase: SignInUseCase,
    coroutineDispatcher: CoroutinesDispatchers
) : ViewModel() {

    val auth = SignInLiveData(
        commons,
        authSignInUseCase,
        coroutineDispatcher
    )

    val email = EmailValidatorLiveData(validatorUseCase)
    val password = PasswordValidatorLiveData(validatorUseCase)

    val formIsValid: LiveData<Boolean>
        get() = PairMediatorLiveData(email.isValid, password.isValid)
            .distinctUntilChanged()
            .switchMap { state ->
                val firstValue = state.first?.valid ?: false
                val secondValue = state.second?.valid ?: false

                return@switchMap liveData {
                    if (firstValue and secondValue) emit(firstValue and secondValue)
                }
            }

    fun submit(data: SignInBodyRequestEntity) {
        auth.signIn(data)
    }
}
