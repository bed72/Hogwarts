package com.bed.seller.presentation.ui.auth.tokens

import androidx.lifecycle.ViewModel

import com.bed.seller.domain.usecases.auth.AuthUseCase
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers
import com.bed.seller.domain.usecases.storage.GetStorageUseCase
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.auth.tokens.states.RefreshTokenLiveData
import com.bed.seller.presentation.ui.storage.states.GetValueInStorageLiveData
import com.bed.seller.presentation.ui.storage.states.SaveValueInStorageLiveData

class TokensViewModel(
    commons: Commons,
    authUseCase: AuthUseCase,
    getStorageUseCase: GetStorageUseCase,
    saveStorageUseCase: SaveStorageUseCase,
    coroutineDispatcher: CoroutinesDispatchers,
) : ViewModel() {

    val getRefreshToken = GetValueInStorageLiveData(
        getStorageUseCase,
        coroutineDispatcher
    )

    val saveRefreshToken = SaveValueInStorageLiveData(
        saveStorageUseCase,
        coroutineDispatcher
    )

    val refreshToken = RefreshTokenLiveData(
        commons,
        authUseCase,
        coroutineDispatcher
    )
}
