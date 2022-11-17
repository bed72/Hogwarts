package com.bed.seller.presentation.ui.storage.states

import com.bed.seller.R

import androidx.annotation.StringRes

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase

class SaveValueInStorageLiveData(
    private val useCase: SaveStorageUseCase,
    private val coroutineDispatcher: Coroutines
) {
    private val actions = MutableLiveData<Actions>()

    val state: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutineDispatcher.main()) {
                if (action is Actions.Save) {
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

    fun save(params: Pair<String, String>) { actions.value = Actions.Save(params) }

    sealed class Actions { data class Save(val params: Pair<String, String>) : Actions() }

    sealed class States {
        object Loading : States()
        data class Success(val data: String) : States()
        data class Failure(@StringRes val message: Int) : States()
    }
}
