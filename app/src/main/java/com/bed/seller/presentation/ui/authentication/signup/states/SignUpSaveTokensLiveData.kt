package com.bed.seller.presentation.ui.authentication.signup.states

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.core.usecases.storage.SaveStorageUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase


class SignUpSaveTokensLiveData(
    private val coroutinesUseCase: CoroutinesUseCase,
    private val saveStorageUseCase: SaveStorageUseCase
) {

    private val action = MutableLiveData<Actions>()

    val state: LiveData<States> = action.switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            emit(States.Loading)

            if (action is Actions.Save)
                action.values.forEach { value ->
                    saveStorageUseCase(value).collect { emit(States.Continue) }
                }
        }
    }

    fun save(value: List<Pair<String, String>>) {
        action.value = Actions.Save(value)
    }

    sealed class Actions {
        data class Save(val values: List<Pair<String, String>>) : Actions()
    }

    sealed class States {
        data object Loading : States()
        data object Continue : States()
    }
}
