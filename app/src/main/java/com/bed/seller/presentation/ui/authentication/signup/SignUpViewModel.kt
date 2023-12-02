package com.bed.seller.presentation.ui.authentication.signup

import javax.inject.Inject

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.seller.presentation.commons.states.FormState

import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.domain.models.authentication.AuthenticationModel
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

@HiltViewModel
class SignUpViewModel @Inject constructor(
    signUpUseCase: SignUpUseCase,
    coroutinesUseCase: CoroutinesUseCase
) : ViewModel() {

    private val actions = MutableLiveData<Actions>()

    val email = FormState()
    val password = FormState()

    val states: LiveData<States> = actions.switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            if (action is Actions.SignUp) {
                emit(States.Loading)

                signUpUseCase(action.parameter).collect { data ->
                    data.fold(
                        { failure -> emit(States.Failure(failure.message)) },
                        { success -> emit(States.Success(success)) }
                    )
                }
            }
        }
    }

    fun signUp(parameter: AuthenticationParameter) {
        actions.value = Actions.SignUp(parameter)
    }

    sealed class Actions {
        data class SignUp(val parameter: AuthenticationParameter) : Actions()
    }

    sealed class States {
        data object Loading : States()
        data class Failure(val data: String) : States()
        data class Success(val data: AuthenticationModel) : States()
    }
}
