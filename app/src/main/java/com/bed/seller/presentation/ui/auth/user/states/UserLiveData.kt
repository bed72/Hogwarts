package com.bed.seller.presentation.ui.auth.user.states

import com.bed.seller.R

import androidx.annotation.StringRes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.presentation.ui.common.Commons

import com.bed.seller.infrastructure.storage.StorageConstants

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.usecases.auth.UserUseCase
import com.bed.seller.domain.usecases.storage.StorageUseCase
import com.bed.seller.domain.entities.auth.user.UserResponseEntity

class UserLiveData(
    private val commons: Commons,
    private val coroutines: Coroutines,
    private val userUseCase: UserUseCase,
    private val storageUseCase: StorageUseCase
) {
    private val actions = MutableLiveData<Actions>()

    val state: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutines.main()) {
                if (action is Actions.GetUser) {
                    emit(States.Loading)

                    storageUseCase.get(StorageConstants.DATA_STORE_ACCESS_TOKEN).collect { data ->
                        if (data.isEmpty()) emit(States.Failure())
                        else userUseCase(buildBodyParams()).collect { response ->
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
                                            R.string.user_welcome_success
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

    fun get() {
        actions.value = Actions.GetUser
    }

    private fun buildBodyParams() = UserUseCase.Params(PathEntity.GET_USER)

    sealed class Actions {
        object GetUser : Actions()
    }

    sealed class States {
        object Loading : States()
        data class Failure(@StringRes val message: Int = 0) : States()
        data class Success(val data: UserResponseEntity, @StringRes val message: Int) : States()
    }
}
