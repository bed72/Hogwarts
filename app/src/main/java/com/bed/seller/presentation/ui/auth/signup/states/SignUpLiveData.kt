package com.bed.seller.presentation.ui.auth.signup.states

import com.bed.seller.R

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap

import androidx.lifecycle.MutableLiveData

import com.bed.seller.presentation.ui.auth.commons.Auth

import com.bed.seller.infrastructure.storage.StorageConstants

import com.bed.seller.domain.usecases.auth.AuthUseCase
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.auth.signup.SignUpBodyRequestEntity

class SignUpLiveData(
    private val authCommons: Auth,
    private val authUseCase: AuthUseCase,
    private val coroutineDispatcher: CoroutinesDispatchers
) {
    private val actions = MutableLiveData<Auth.Actions>()

    val state: LiveData<Auth.States> = actions
        .switchMap { action ->
            liveData(coroutineDispatcher.main()) {
                if (action is Auth.Actions.SignUp) {
                    emit(Auth.States.Loading)

                    authUseCase(buildBodyParams(action)).collect { response ->
                        response.fold(
                            { failure ->
                                emit(Auth.States.Failure(authCommons.mapper(failure.status)))
                            },
                            { success ->
                                saveInStorage(success.data)

                                emit(
                                    Auth.States.Success(
                                        success.data,
                                        R.string.sign_up_success_create_account
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }

    fun signUp(params: SignUpBodyRequestEntity) {
        actions.value = Auth.Actions.SignUp(params)
    }

    private fun buildBodyParams(action: Auth.Actions.SignUp) =
        AuthUseCase.Params(PathEntity.SIGN_UP, action.params)

    private suspend fun saveInStorage(data: AuthResponseEntity) {
        authCommons.saveInStorage(
            StorageConstants.DATA_STORE_ACCESS_TOKEN to data.accessToken,
            StorageConstants.DATA_STORE_REFRESH_TOKEN to data.refreshToken,
            StorageConstants.DATA_STORE_EXPIRES_IN to data.expiresIn.toString()
        )
    }
}
