package com.bed.seller.presentation.ui.auth.refresh.states

import androidx.annotation.StringRes

import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import com.bed.seller.presentation.ui.common.Commons

import com.bed.seller.domain.usecases.auth.RefreshUseCase
import com.bed.seller.domain.dispatchers.Coroutines

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.tokens.isNotEmpty
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.auth.tokens.RefreshTokenBodyRequestEntity

class RefreshLiveData(
    private val commons: Commons,
    private val coroutines: Coroutines,
    private val refreshUseCase: RefreshUseCase
) {
    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions
        .switchMap { action ->
            liveData(coroutines.main()) {
                if (action is Actions.RefreshToken) {
                    emit(States.Loading)

                    refreshUseCase(buildBodyParams(action)).collect { response ->
                        response.fold(
                            { failure -> emit(States.Failure(commons.mapper(failure.data.message))) },
                            { success -> emit(States.Success(success.data)) }
                        )
                    }
                }
            }
        }

    fun refreshToken(params: RefreshTokenBodyRequestEntity) {
        if (params.isNotEmpty()) actions.value = Actions.RefreshToken(params)
    }

    private fun buildBodyParams(action: Actions.RefreshToken) =
        RefreshUseCase.Params(PathEntity.REFRESH_TOKEN, action.params)

    sealed class Actions {
        data class RefreshToken(val params: RefreshTokenBodyRequestEntity) : Actions()
    }

    sealed class States {
        object Loading : States()
        data class Failure(@StringRes val message: Int) : States()
        data class Success(val data: AuthResponseEntity) : States()
    }
}
