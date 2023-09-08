package com.bed.seller.presentation.ui.authentication.reset

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.seller.presentation.commons.states.PasswordState

@HiltViewModel
class ResetViewModel @Inject constructor(
    coroutinesUseCase: CoroutinesUseCase
) : ViewModel() {
    val password = PasswordState(coroutinesUseCase)
    val repeatPassword = PasswordState(coroutinesUseCase)
}
