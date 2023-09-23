package com.bed.seller.presentation.commons.states

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.core.values.Email

import com.bed.core.usecases.coroutines.CoroutinesUseCase

class EmailState(useCase: CoroutinesUseCase) {

    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions.switchMap { action ->
        liveData(useCase.main()) {
            if (action is Actions.Validate)
                Email(action.parameter).apply {
                    if (isValid) emit(States.Success(this)) else emit(States.Failure(message))
                }
        }
    }

    fun set(parameter: String) {
        actions.value = Actions.Validate(parameter)
    }

    sealed class Actions {
        data class Validate(val parameter: String) : Actions()
    }

    sealed class States {
        data class Success(val data: Email) : States()
        data class Failure(val data: String? = "Opss") : States()
    }
}
