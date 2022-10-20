package com.bed.seller.presentation.ui.auth.tokens.states

import com.bed.seller.R

import androidx.annotation.StringRes

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.infrastructure.storage.StorageConstants
import com.bed.seller.domain.usecases.storage.GetStorageUseCase

class GetTokensLiveData(
    private val getStorageUseCase: GetStorageUseCase,
    private val coroutineDispatcher: CoroutinesDispatchers
) {
    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions
        .distinctUntilChanged()
        .switchMap { action ->
            liveData(coroutineDispatcher.main()) {
                if (action is Actions.GetToken) {
                    emit(States.Loading)

                    getStorageUseCase(StorageConstants.DATA_STORE_REFRESH_TOKEN).collect { value ->
                        if (value.isNotEmpty()) emit(States.Success(value))
                        else emit(States.Failure(R.string.app_name))
                    }
                }
            }
        }

    fun getToken(data: Pair<String, String>) {
        actions.value = Actions.GetToken(data)
    }

    sealed class Actions {
        data class GetToken(val params: Pair<String, String>) : Actions()
    }

    sealed class States {
        object Loading : States()
        data class Success(val data: String) : States()
        data class Failure(@StringRes val message: Int) : States()
    }
}
