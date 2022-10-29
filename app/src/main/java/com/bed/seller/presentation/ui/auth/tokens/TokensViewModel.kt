package com.bed.seller.presentation.ui.auth.tokens

import androidx.lifecycle.ViewModel

import com.bed.seller.domain.usecases.auth.AuthRefreshUseCase
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers
import com.bed.seller.domain.usecases.storage.GetStorageUseCase

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.auth.tokens.states.RefreshLiveData
import com.bed.seller.presentation.ui.storage.states.GetValueInStorageLiveData

class TokensViewModel(
    commons: Commons,
    getStorageUseCase: GetStorageUseCase,
    authRefreshUseCase: AuthRefreshUseCase,
    coroutineDispatcher: CoroutinesDispatchers,
) : ViewModel() {

    val tokens = GetValueInStorageLiveData(
        getStorageUseCase,
        coroutineDispatcher
    )

    val refresh = RefreshLiveData(
        commons,
        authRefreshUseCase,
        coroutineDispatcher
    )
}
