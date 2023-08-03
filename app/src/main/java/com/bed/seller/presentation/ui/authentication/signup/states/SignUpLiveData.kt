package com.bed.seller.presentation.ui.authentication.signup.states

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.domain.models.authentication.SignUpModel
import com.bed.core.domain.parameters.authentication.SignUpParameters

import com.bed.seller.presentation.ui.authentication.signup.states.SignUpLiveData.States.Loading
import com.bed.seller.presentation.ui.authentication.signup.states.SignUpLiveData.States.Failure
import com.bed.seller.presentation.ui.authentication.signup.states.SignUpLiveData.States.Success

class SignUpLiveData(
    private val signUpUseCase: SignUpUseCase,
    private val coroutinesUseCase: CoroutinesUseCase
) {

    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions.switchMap { action ->
            liveData(coroutinesUseCase.main()) {
                if (action is Actions.MagicLink) {
                    emit(Loading)

                    signUpUseCase(action.params).collect { data ->
                        data.fold(
                            { failure -> emit(Failure(failure.message)) },
                            { success -> emit(Success(success)) }
                        )
                    }
                }
            }
        }

    fun magicLink(params: SignUpParameters) {
        actions.value = Actions.MagicLink(params)
    }

    sealed class Actions {
        data class MagicLink(val params: SignUpParameters) : Actions()
    }

    sealed class States {
        object Loading : States()
        data class Failure(val message: String) : States()
        data class Success(val data: SignUpModel) : States()
    }
}
