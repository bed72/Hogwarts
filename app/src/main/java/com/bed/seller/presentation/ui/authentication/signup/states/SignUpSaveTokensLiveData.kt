package com.bed.seller.presentation.ui.authentication.signup.states

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.core.usecases.storage.SaveStorageUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.seller.presentation.ui.authentication.signup.states.SignUpSaveTokensLiveData.Actions.Save

import com.bed.seller.presentation.ui.authentication.signup.states.SignUpSaveTokensLiveData.States.Loading
import com.bed.seller.presentation.ui.authentication.signup.states.SignUpSaveTokensLiveData.States.Continue

class SignUpSaveTokensLiveData(
    private val coroutinesUseCase: CoroutinesUseCase,
    private val saveStorageUseCase: SaveStorageUseCase
) {

    private val action = MutableLiveData<Actions>()

    val state: LiveData<States> = action.switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            emit(Loading)

            if (action is Save)
                action.values.forEach { value ->
                    saveStorageUseCase(value).collect { emit(Continue) }
                }
        }
    }

    fun save(value: List<Pair<String, String>>) {
        action.value = Save(value)
    }

    sealed class Actions {
        data class Save(val values: List<Pair<String, String>>) : Actions()
    }

    sealed class States {
        object Loading : States()
        object Continue : States()
    }

}
