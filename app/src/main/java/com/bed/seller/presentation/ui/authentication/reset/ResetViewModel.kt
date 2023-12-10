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

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.FormState

import com.bed.core.usecases.authentication.ResetUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.domain.parameters.authentication.ResetParameter

@HiltViewModel
class ResetViewModel @Inject constructor(
    private val resetUseCase: ResetUseCase,
    private val coroutinesUseCase: CoroutinesUseCase
) : ViewModel() {
    val password = FormState()

    private val _state = MutableStateFlow<States<Boolean>>(States.Initial)
    val state: StateFlow<States<Boolean>> get() = _state.asStateFlow()

    fun reset(parameter: ResetParameter) {
        _state.update { States.Loading }

        viewModelScope.launch(coroutinesUseCase.main()) {
            _state.update { States.Success(resetUseCase(parameter).first()) }
        }
    }
}
