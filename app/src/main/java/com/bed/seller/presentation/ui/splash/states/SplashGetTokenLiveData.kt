package com.bed.seller.presentation.ui.splash.states

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bed.core.usecases.coroutines.CoroutinesUseCase
import com.bed.core.usecases.storage.GetStorageUseCase
import com.bed.seller.presentation.ui.splash.states.SplashGetTokenLiveData.Actions.Get
import com.bed.seller.presentation.ui.splash.states.SplashGetTokenLiveData.States.Failure
import com.bed.seller.presentation.ui.splash.states.SplashGetTokenLiveData.States.Success

class SplashGetTokenLiveData(
    private val coroutinesUseCase: CoroutinesUseCase,
    private val getStorageUseCase: GetStorageUseCase
) {

    private val action = MutableLiveData<Actions>()

    val state: LiveData<States> = action.switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            if (action is Get) {
                getStorageUseCase(action.values).collect { data ->
                    if (data.isEmpty()) emit(Failure) else emit(Success)
                }
            }
        }
    }

    fun accessToken(data: String) {
        action.value = Get(data)
    }

    sealed class Actions {
        data class Get(val values: String) : Actions()
    }

    sealed class States {
        object Failure : States()
        object Success : States()
    }
}
