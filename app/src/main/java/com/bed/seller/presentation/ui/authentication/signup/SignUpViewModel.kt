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

import com.bed.core.usecases.authentication.SignUpUsecase

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.FormState

import com.bed.core.entities.output.AuthenticationOutput
import com.bed.core.entities.input.AuthenticationInput

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUsecase) : ViewModel() {
    val email = FormState()
    val password = FormState()

    private val _state = MutableStateFlow<States<AuthenticationOutput>>(States.Initial)
    val state: StateFlow<States<AuthenticationOutput>> get() = _state.asStateFlow()

    fun signUp(parameter: AuthenticationInput) {
        _state.update { States.Loading }

        viewModelScope.launch {
            signUpUseCase(parameter).collect {
                it.fold(
                    { failure -> _state.update { States.Failure(failure.message) } },
                    { success -> _state.update { States.Success(success) } }
                )
            }
        }
    }
}
