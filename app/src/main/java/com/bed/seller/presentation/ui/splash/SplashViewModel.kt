package com.bed.seller.presentation.ui.splash

import javax.inject.Inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.storage.GetStorageUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.framework.constants.StorageConstant

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val storageUseCase: GetStorageUseCase,
    private val coroutinesUseCase: CoroutinesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<States<Nothing>>(States.Loading)
    val state: StateFlow<States<Nothing>> get() = _state.asStateFlow()

    init { get() }

    private fun get() {
        viewModelScope.launch(coroutinesUseCase.main()) {
            _state.update { States.Loading }

            storageUseCase(StorageConstant.DATASTORE_REFRESH_TOKEN.value).collect { data ->
                _state.update { if (data.isEmpty()) States.Failure() else States.Success() }
            }
        }
    }
}
