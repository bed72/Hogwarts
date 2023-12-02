package com.bed.seller.presentation.ui.splash

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.authentication.IsLoggedInUseCase

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: IsLoggedInUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<States>(States.Loading)
    val state: StateFlow<States> get() = _state.asStateFlow()

    init { isLoggedIn() }

    private fun isLoggedIn() {
        _state.update { States.IsLoggedIn(useCase()) }
    }

    sealed class States {
        data object Loading : States()
        data class IsLoggedIn(val isSuccess: Boolean) : States()
    }
}
