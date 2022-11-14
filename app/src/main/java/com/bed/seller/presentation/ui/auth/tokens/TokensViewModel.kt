package com.bed.seller.presentation.ui.auth.tokens

import androidx.lifecycle.ViewModel

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers
import com.bed.seller.domain.usecases.storage.GetStorageUseCase

import com.bed.seller.presentation.ui.storage.states.GetValueInStorageLiveData

class TokensViewModel(
    getStorageUseCase: GetStorageUseCase,
    coroutineDispatcher: CoroutinesDispatchers,
) : ViewModel() {

    val tokens = GetValueInStorageLiveData(
        getStorageUseCase,
        coroutineDispatcher
    )
}
