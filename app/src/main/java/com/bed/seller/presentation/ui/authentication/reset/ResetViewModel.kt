package com.bed.seller.presentation.ui.authentication.reset

import javax.inject.Inject

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.seller.presentation.commons.states.FormState

import com.bed.core.usecases.authentication.ResetUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.domain.parameters.authentication.ResetParameter

@HiltViewModel
class ResetViewModel @Inject constructor(
    resetUseCase: ResetUseCase,
    coroutinesUseCase: CoroutinesUseCase
) : ViewModel() {
    private val actions = MutableLiveData<Actions>()

    val password = FormState()

    val states: LiveData<States> = actions.distinctUntilChanged().switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            emit(States.Loading)

            if (action is Actions.Reset)
                resetUseCase(action.parameter).collect { emit(States.Reset(it)) }
        }
    }

    fun reset(parameter: ResetParameter) {
        actions.value = Actions.Reset(parameter)
    }

    sealed class Actions {
        data class Reset(val parameter: ResetParameter) : Actions()
    }

    sealed class States {
        data object Loading : States()

        data class Reset(val isSuccess: Boolean) : States()
    }
}
