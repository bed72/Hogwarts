package com.bed.seller.presentation.ui.splash

import javax.inject.Inject

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.storage.GetStorageUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val coroutinesUseCase: CoroutinesUseCase,
    private val getStorageUseCase: GetStorageUseCase,
) : ViewModel() {
    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions.switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            if (action is Actions.Get) {
                getStorageUseCase(action.values).collect { data ->
                    if (data.isEmpty()) emit(States.Failure) else emit(States.Success)
                }
            }
        }
    }

    fun getAccessToken(data: String) {
        actions.value = Actions.Get(data)
    }

    sealed class Actions {
        data class Get(val values: String) : Actions()
    }

    sealed class States {
        data object Failure : States()
        data object Success : States()
    }
}
