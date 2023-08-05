package com.bed.seller.presentation.ui.splash.states

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.core.usecases.storage.GetStorageUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

class SplashGetTokenLiveData(
    private val coroutinesUseCase: CoroutinesUseCase,
    private val getStorageUseCase: GetStorageUseCase
) {

    private val action = MutableLiveData<Actions>()

    val state: LiveData<States> = action.switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            if (action is Actions.Get) {
                getStorageUseCase(action.values).collect { data ->
                    if (data.isEmpty()) emit(States.Failure) else emit(States.Success)
                }
            }
        }
    }

    fun accessToken(data: String) {
        action.value = Actions.Get(data)
    }

    sealed class Actions {
        data class Get(val values: String) : Actions()
    }

    sealed class States {
        data object Failure : States()
        data object Success : States()
    }
}
