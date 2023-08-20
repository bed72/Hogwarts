package com.bed.seller.presentation.commons.states

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.core.values.EmailValue
import com.bed.core.usecases.coroutines.CoroutinesUseCase
import com.bed.core.values.PasswordValue

class PasswordState(useCase: CoroutinesUseCase) {

    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions.switchMap { action ->
        liveData(useCase.main()) {
            if (action is Actions.Validate)
                PasswordValue(action.parameter).validate().fold(
                    { failure -> emit(States.Failure(failure)) },
                    { success -> emit(States.Success(success)) }
                )
        }
    }

    fun set(parameter: String) {
        actions.value = Actions.Validate(parameter)
    }

    sealed class Actions {
        data class Validate(val parameter: String) : Actions()
    }

    sealed class States {
        data class Failure(val data: String) : States()
        data class Success(val data: PasswordValue) : States()
    }
}
