package com.bed.seller.presentation.ui.auth.tokens

import androidx.lifecycle.ViewModel

import com.bed.seller.domain.usecases.auth.AuthUseCase
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.auth.tokens.states.RefreshTokenLiveData

class TokensViewModel(
    commons: Commons,
    authUseCase: AuthUseCase,
    coroutineDispatcher: CoroutinesDispatchers,
) : ViewModel() {

    val auth = RefreshTokenLiveData(
        commons,
        authUseCase,
        coroutineDispatcher
    )
}
