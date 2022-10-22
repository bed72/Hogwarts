package com.bed.seller.presentation.ui.auth.tokens.states

import com.bed.seller.R

import androidx.annotation.StringRes

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.presentation.ui.common.Commons

import com.bed.seller.domain.usecases.auth.AuthUseCase
import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers
import com.bed.seller.domain.entities.auth.tokens.RefreshTokenEntity

class RefreshTokenLiveData(
    private val commons: Commons,
    private val authUseCase: AuthUseCase,
    private val coroutineDispatcher: CoroutinesDispatchers
) {
    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutineDispatcher.main()) {
                if (action is Actions.RefreshToken) {
                    emit(States.Loading)

                    authUseCase(buildBodyParams(action)).collect { response ->
                        response.fold(
                            { failure -> emit(States.Failure(commons.mapper(failure.status))) },
                            { success -> emit(States.Success(success.data, R.string.sign_in_success)) }
                        )
                    }

                    emit(States.Empty)
                }
            }
        }

    fun refreshToken(params: RefreshTokenEntity) {
        actions.value = Actions.RefreshToken(params)
    }

    private fun buildBodyParams(action: Actions.RefreshToken) =
        AuthUseCase.Params(PathEntity.REFRESH_TOKEN, action.params)

    sealed class Actions {
        data class RefreshToken(val params: RefreshTokenEntity) : Actions()
    }

    sealed class States {
        object Empty : States()
        object Loading : States()
        data class Failure(@StringRes val message: Int) : States()
        data class Success(val data: AuthResponseEntity, @StringRes val message: Int) : States()
    }
}
