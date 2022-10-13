package com.bed.seller.presentation.ui.auth.signup.states

import com.bed.seller.R

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap

import androidx.lifecycle.MutableLiveData

import com.bed.seller.infrastructure.storage.StorageConstants

import com.bed.seller.presentation.ui.auth.commons.Auth

import com.bed.seller.domain.entities.auth.AuthRequestEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers
import com.bed.seller.domain.usecases.auth.signup.SignUpUseCase

class SignUpLiveData(
    private val commons: Auth,
    private val signUpUseCase: SignUpUseCase,
    private val coroutineDispatcher: CoroutinesDispatchers
) {
    private val actions = MutableLiveData<Auth.Actions>()

    val state: LiveData<Auth.States> = actions
        .switchMap { action ->
            liveData(coroutineDispatcher.main()) {
                if (action is Auth.Actions.SignUp) {
                    emit(Auth.States.Loading)

                    signUpUseCase(action.params).collect { response ->
                        response.fold(
                            { failure ->
                                val status = failure.status
                                val bed = commons.mapper(status)
                                emit(Auth.States.Failure(bed))
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

    fun createAccount(params: AuthRequestEntity) {
        actions.value = Auth.Actions.SignUp(params)
    }

    private suspend fun saveInStorage(data: AuthResponseEntity) {
        commons.saveInStorage(
            StorageConstants.DATA_STORE_ACCESS_TOKEN to data.accessToken,
            StorageConstants.DATA_STORE_REFRESH_TOKEN to data.refreshToken,
            StorageConstants.DATA_STORE_EXPIRES_IN to data.expiresIn.toString()
        )
    }
}
