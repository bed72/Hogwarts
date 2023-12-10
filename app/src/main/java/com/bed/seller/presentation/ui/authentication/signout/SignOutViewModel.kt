package com.bed.seller.presentation.ui.authentication.signout

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.seller.presentation.commons.states.States
import com.bed.core.usecases.authentication.SignOutUseCase

@HiltViewModel
class SignOutViewModel @Inject constructor(
    private val useCase: SignOutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<States<Unit>>(States.Initial)
    val state: StateFlow<States<Unit>> get() = _state.asStateFlow()

    fun signOut() {
        with (_state) {
            update { States.Loading }
            update { States.Success(useCase()) }
        }
    }
}
