package com.bed.seller.presentation.ui.auth.signin.states

import com.bed.seller.R

import androidx.annotation.StringRes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.presentation.ui.common.Commons

import com.bed.seller.domain.usecases.auth.AuthSignInUseCase
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.auth.signin.SignInBodyRequestEntity

class SignInLiveData(
    private val commons: Commons,
    private val authSignInUseCase: AuthSignInUseCase,
    private val coroutineDispatcher: CoroutinesDispatchers
) {
    private val actions = MutableLiveData<Actions>()

    val state: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutineDispatcher.main()) {
                if (action is Actions.SignIn) {
                    emit(States.Loading)

                    authSignInUseCase(buildBodyParams(action)).collect { response ->
                        response.fold(
                            { failure -> emit(States.Failure(commons.mapper(failure.status))) },
                            { success -> emit(States.Success(success.data, R.string.sign_in_success)) }
                        )
                    }

                    emit(States.Empty)
                }
            }
        }

    fun signIn(params: SignInBodyRequestEntity) {
        actions.value = Actions.SignIn(params)
    }

    private fun buildBodyParams(action: Actions.SignIn) =
        AuthSignInUseCase.Params(PathEntity.SIGN_IN, action.params)

    sealed class Actions {
        data class SignIn(val params: SignInBodyRequestEntity) : Actions()
    }

    sealed class States {
        object Empty : States()
        object Loading : States()
        data class Failure(@StringRes val message: Int) : States()
        data class Success(val data: AuthResponseEntity, @StringRes val message: Int) : States()
    }
}
