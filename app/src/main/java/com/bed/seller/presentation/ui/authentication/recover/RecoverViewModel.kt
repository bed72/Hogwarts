package com.bed.seller.presentation.ui.authentication.recover

import javax.inject.Inject

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.seller.presentation.commons.states.EmailState

import com.bed.core.usecases.coroutines.CoroutinesUseCase
import com.bed.core.usecases.authentication.RecoverUseCase

import com.bed.core.domain.parameters.authentication.RecoverParameter

@HiltViewModel
class RecoverViewModel @Inject constructor(
    coroutinesUseCase: CoroutinesUseCase,
    recoverUseCase: RecoverUseCase,
) : ViewModel() {
    private val actions = MutableLiveData<Actions>()

    val email = EmailState(coroutinesUseCase)

    val states: LiveData<States> = actions.distinctUntilChanged().switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            emit(States.Loading)

            if (action is Actions.PasswordReset)
                recoverUseCase(action.parameter).collect { emit(States.Recover(it)) }
        }
    }

    fun recover(parameter: RecoverParameter) {
        actions.value = Actions.PasswordReset(parameter)
    }

    sealed class Actions {
        data class PasswordReset(val parameter: RecoverParameter) : Actions()
    }

    sealed class States {
        data object Loading : States()
        data class Recover(val isSuccess: Boolean) : States()
    }
}
