package com.bed.seller.presentation.ui.splash

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.authentication.IsLoggedInUsecase

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: IsLoggedInUsecase
) : ViewModel() {
    private val _state = MutableStateFlow<States>(States.Initial)
    val state: StateFlow<States> get() = _state.asStateFlow()

    fun isLoggedIn() {
        _state.update { States.IsLoggedIn(useCase()) }
    }

    sealed class States {
        data object Initial : States()
        data class IsLoggedIn(val isLogged: Boolean) : States()
    }
}
