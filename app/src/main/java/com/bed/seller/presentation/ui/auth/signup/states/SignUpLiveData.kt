package com.bed.seller.presentation.ui.auth.signup.states

import com.bed.seller.R

import androidx.annotation.StringRes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.presentation.ui.common.Commons

import com.bed.seller.domain.usecases.auth.AuthUseCase
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.auth.signup.SignUpBodyRequestEntity

class SignUpLiveData(
    private val commons: Commons,
    private val authUseCase: AuthUseCase,
    private val coroutineDispatcher: CoroutinesDispatchers
) {
    private val actions = MutableLiveData<Actions>()

    val state: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutineDispatcher.main()) {
                if (action is Actions.SignUp) {
                    emit(States.Loading)

                    authUseCase(buildBodyParams(action)).collect { response ->
                        response.fold(
                            { failure -> emit(States.Failure(commons.mapper(failure.status))) },
                            { success ->
//                                saveInStorage(success.data)

                                emit(
                                    States.Success(success.data, R.string.sign_up_success_create_account)
                                )
                            }
                        )
                    }

                    emit(States.Empty)
                }
            }
        }

    fun signUp(params: SignUpBodyRequestEntity) {
        actions.value = Actions.SignUp(params)
    }

    private fun buildBodyParams(action: Actions.SignUp) =
        AuthUseCase.Params(PathEntity.SIGN_UP, action.params)

    sealed class Actions {
        data class SignUp(val params: SignUpBodyRequestEntity) : Actions()
    }

    sealed class States {
        object Empty : States()
        object Loading : States()
        data class Failure(@StringRes val message: Int) : States()
        data class Success(val data: AuthResponseEntity, @StringRes val message: Int) : States()
    }
}
