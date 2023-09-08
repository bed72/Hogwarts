package com.bed.seller.presentation.ui.splash

import javax.inject.Inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.authentication.IsLoggedInUseCase

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<States>(States.Loading)

    val state: StateFlow<States> = _state.asStateFlow()

    fun isLoggedIn() {
        viewModelScope.launch {
            isLoggedInUseCase().onStart { onLoading() }.collect { onSuccess(it) }
        }
    }

    private fun onLoading() {
        _state.update { States.Loading }
    }

    private fun onSuccess(success: Boolean) {
        _state.update { States.IsLoggedIn(success) }
    }

    sealed class States {
        data object Loading : States()
        data class IsLoggedIn(val isSuccess: Boolean) : States()
    }
}
