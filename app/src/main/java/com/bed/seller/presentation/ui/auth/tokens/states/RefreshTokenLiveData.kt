package com.bed.seller.presentation.ui.auth.tokens.states

import com.bed.seller.R

import androidx.annotation.StringRes

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.presentation.ui.common.Commons

import com.bed.seller.domain.usecases.auth.AuthRefreshUseCase
import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers
import com.bed.seller.domain.entities.auth.tokens.RefreshTokenBodyRequestEntity

class RefreshTokenLiveData(
    private val commons: Commons,
    private val authRefreshUseCase: AuthRefreshUseCase,
    private val coroutineDispatcher: CoroutinesDispatchers
) {
    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutineDispatcher.main()) {
                if (action is Actions.RefreshToken) {
                    emit(States.Loading)

                    authRefreshUseCase(buildBodyParams(action)).collect { response ->
                        response.fold(
                            { failure -> emit(States.Failure(commons.mapper(failure.status))) },
                            { success -> emit(States.Success(success.data, R.string.sign_in_success)) }
                        )
                    }

                    emit(States.Empty)
                }
            }
        }

    fun refreshToken(params: RefreshTokenBodyRequestEntity) {
        actions.value = Actions.RefreshToken(params)
    }

    private fun buildBodyParams(action: Actions.RefreshToken) =
        AuthRefreshUseCase.Params(PathEntity.REFRESH_TOKEN, action.params)

    sealed class Actions {
        data class RefreshToken(val params: RefreshTokenBodyRequestEntity) : Actions()
    }

    sealed class States {
        object Empty : States()
        object Loading : States()
        data class Failure(@StringRes val message: Int) : States()
        data class Success(val data: AuthResponseEntity, @StringRes val message: Int) : States()
    }
}
