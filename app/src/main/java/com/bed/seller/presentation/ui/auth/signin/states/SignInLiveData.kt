package com.bed.seller.presentation.ui.auth.signin.states

import androidx.annotation.StringRes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.R

import com.bed.seller.presentation.ui.common.Commons

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.auth.SignInUseCase

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.auth.signin.SignInBodyRequestEntity

class SignInLiveData(
    private val commons: Commons,
    private val coroutines: Coroutines,
    private val signInUseCase: SignInUseCase,
) {
    private val actions = MutableLiveData<Actions>()

    val state: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutines.main()) {
                if (action is Actions.SignIn) {
                    emit(States.Loading)

                    signInUseCase(buildBodyParams(action)).collect { response ->
                        response.fold(
                            { failure ->
                                val message = failure.data.message.ifEmpty {
                                    failure.data.errorDescription
                                }
                                emit(States.Failure(commons.mapper(message)))
                            },
                            { success ->

                                emit(
                                    States.Success(
                                        success.data,
                                        R.string.user_welcome_success,
                                        success.data.user.userMetadata.name
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }

    fun signIn(params: SignInBodyRequestEntity) {
        actions.value = Actions.SignIn(params)
    }

    private fun buildBodyParams(action: Actions.SignIn) =
        SignInUseCase.Params(PathEntity.SIGN_IN, action.params)

    sealed class Actions {
        data class SignIn(val params: SignInBodyRequestEntity) : Actions()
    }

    sealed class States {
        object Loading : States()
        data class Failure(@StringRes val message: Int) : States()
        data class Success(
            val data: AuthResponseEntity,
            @StringRes val message: Int,
            val arg: String
        ) : States()
    }
}
