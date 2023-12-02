package com.bed.seller.presentation.ui.authentication.signout

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.authentication.SignOutUseCase

@HiltViewModel
class SignOutViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<States>(States.Initial)

    val state: StateFlow<States> get() = _state.asStateFlow()

    fun signOut() {
        onLoading()

        signOutUseCase().run { onSuccess() }
    }

    private fun onLoading() {
        _state.update { States.Loading }
    }

    private fun onSuccess() {
        _state.update { States.IsSignOut(true) }
    }

    sealed class States {
        data object Initial : States()
        data object Loading : States()
        data class IsSignOut(val isSuccess: Boolean) : States()
    }
}
