package com.bed.seller.presentation.ui.authentication.signin

import javax.inject.Inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.entities.input.AuthenticationInput
import com.bed.core.entities.output.AuthenticationOutput

import com.bed.core.usecases.authentication.SignInUsecase

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.FormState

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUsecase) : ViewModel() {
    val email = FormState()
    val password = FormState()

    private val _state = MutableStateFlow<States<AuthenticationOutput>>(States.Initial)
    val state: StateFlow<States<AuthenticationOutput>> get() = _state.asStateFlow()

    fun signIn(parameter: AuthenticationInput) {
        _state.update { States.Loading }

        viewModelScope.launch {
            signInUseCase(parameter).collect {
                it.fold(
                    { failure -> _state.update { States.Failure(failure.message) } },
                    { success -> _state.update { States.Success(success) } }
                )
            }
        }
    }
}
