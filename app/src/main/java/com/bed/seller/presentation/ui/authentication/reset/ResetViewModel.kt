package com.bed.seller.presentation.ui.authentication.reset

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

import com.bed.core.usecases.authentication.ResetUsecase

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.FormState

import com.bed.core.entities.input.ResetInput

@HiltViewModel
class ResetViewModel @Inject constructor(private val resetUseCase: ResetUsecase) : ViewModel() {
    val password = FormState()

    private val _state = MutableStateFlow<States<Boolean>>(States.Initial)
    val state: StateFlow<States<Boolean>> get() = _state.asStateFlow()

    fun reset(parameter: ResetInput) {
        _state.update { States.Loading }

        viewModelScope.launch {
            _state.update { States.Success(resetUseCase(parameter).first()) }
        }
    }
}
