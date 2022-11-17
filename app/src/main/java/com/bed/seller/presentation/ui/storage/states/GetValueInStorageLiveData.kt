package com.bed.seller.presentation.ui.storage.states

import com.bed.seller.R

import androidx.annotation.StringRes

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.storage.GetStorageUseCase

class GetValueInStorageLiveData(
    private val useCase: GetStorageUseCase,
    private val coroutineDispatcher: Coroutines
) {
    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions
        .distinctUntilChanged()
        .switchMap { action ->
            liveData(coroutineDispatcher.main()) {
                if (action is Actions.Get) {
                    emit(States.Loading)

                    useCase(action.params).collect { response ->
                        response.fold(
                            { emit(States.Failure(R.string.generic_failure_message)) },
                            { success -> emit(States.Success(success)) }
                        )
                    }
                }
            }
        }

    fun get(params: String) { actions.value = Actions.Get(params) }

    sealed class Actions { data class Get(val params: String) : Actions() }

    sealed class States {
        object Loading : States()
        data class Success(val data: String) : States()
        data class Failure(@StringRes val message: Int) : States()
    }
}

