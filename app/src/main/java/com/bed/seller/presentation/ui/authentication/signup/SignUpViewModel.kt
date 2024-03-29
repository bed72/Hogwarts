package com.bed.seller.presentation.ui.authentication.signup

import javax.inject.Inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.FormState

import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.domain.models.authentication.AuthenticationModel
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val coroutinesUseCase: CoroutinesUseCase
) : ViewModel() {
    val email = FormState()
    val password = FormState()

    private val _state = MutableStateFlow<States<AuthenticationModel>>(States.Initial)
    val state: StateFlow<States<AuthenticationModel>> get() = _state.asStateFlow()

    fun signUp(parameter: AuthenticationParameter) {
        _state.update { States.Loading }

        viewModelScope.launch(coroutinesUseCase.main()) {
            signUpUseCase(parameter).collect {
                it.fold(
                    { failure -> _state.update { States.Failure(failure.message) } },
                    { success -> _state.update { States.Success(success) } }
                )
            }
        }
    }
}
