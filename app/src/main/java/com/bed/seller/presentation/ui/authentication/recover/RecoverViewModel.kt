package com.bed.seller.presentation.ui.authentication.recover

import javax.inject.Inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.authentication.RecoverUsecase

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.FormState

import com.bed.core.entities.input.RecoverInput

@HiltViewModel
class RecoverViewModel @Inject constructor(private val recoverUseCase: RecoverUsecase) : ViewModel() {
    val email = FormState()

    private val _state = MutableStateFlow<States<Boolean>>(States.Initial)
    val state: StateFlow<States<Boolean>> get() = _state.asStateFlow()

    fun recover(parameter: RecoverInput) {
        _state.update { States.Loading }

        viewModelScope.launch {
            _state.update { States.Success(recoverUseCase(parameter).first()) }
        }
    }
}
