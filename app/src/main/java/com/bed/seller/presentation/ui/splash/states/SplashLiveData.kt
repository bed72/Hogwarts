package com.bed.seller.presentation.ui.splash.states

import androidx.annotation.StringRes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.storage.StorageUseCase

import com.bed.seller.infrastructure.storage.StorageConstants

class SplashLiveData(
    private val coroutines: Coroutines,
    private val storageUseCase: StorageUseCase
) {
    private val actions = MutableLiveData<Actions>()

    val state: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutines.main()) {
                if (action is Actions.VerifyToken) {
                    emit(States.Loading)

                    storageUseCase.getData(StorageConstants.DATA_STORE_ACCESS_TOKEN).collect { data ->
                        if (data.isEmpty()) emit(States.Failure()) else emit(States.Success)
                    }
                }
            }
        }

    fun verify() {
        actions.value = Actions.VerifyToken
    }

    sealed class Actions {
        object VerifyToken : Actions()
    }

    sealed class States {
        object Loading : States()
        object Success : States()
        data class Failure(@StringRes val message: Int = 0) : States()
    }
}
