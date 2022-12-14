package com.bed.seller.presentation.ui.auth.signup.states

import com.bed.seller.R

import androidx.annotation.StringRes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.presentation.ui.common.Commons

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.entities.paths.PathEntity

import com.bed.seller.domain.usecases.auth.SignUpUseCase
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.auth.signup.SignUpBodyRequestEntity

class SignUpLiveData(
    private val commons: Commons,
    private val coroutines: Coroutines,
    private val signUpUseCase: SignUpUseCase,
) {
    private val actions = MutableLiveData<Actions>()

    val state: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutines.main()) {
                if (action is Actions.SignUp) {
                    emit(States.Loading)

                    signUpUseCase(buildBodyParams(action)).collect { response ->
                        response.fold(
                            { failure ->
                                val message = failure.data.message.ifEmpty {
                                    failure.data.errorDescription
                                }
                                emit(States.Failure(commons.mapper(message)))
                            },
                            { success ->
                                emit(
                                    States.Success(success.data, R.string.sign_up_success_create_account)
                                )
                            }
                        )
                    }
                }
            }
        }

    fun signUp(params: SignUpBodyRequestEntity) {
        actions.value = Actions.SignUp(params)
    }

    private fun buildBodyParams(action: Actions.SignUp) =
        SignUpUseCase.Params(PathEntity.SIGN_UP, action.params)

    sealed class Actions {
        data class SignUp(val params: SignUpBodyRequestEntity) : Actions()
    }

    sealed class States {
        object Loading : States()
        data class Failure(@StringRes val message: Int) : States()
        data class Success(val data: AuthResponseEntity, @StringRes val message: Int) : States()
    }
}
