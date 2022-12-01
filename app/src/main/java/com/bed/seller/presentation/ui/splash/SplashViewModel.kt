package com.bed.seller.presentation.ui.splash

import androidx.lifecycle.ViewModel
import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.storage.StorageUseCase

import com.bed.seller.presentation.ui.splash.states.SplashLiveData

class SplashViewModel(
    coroutines: Coroutines,
    storageUseCase: StorageUseCase
) : ViewModel() {
    val splash = SplashLiveData(
        coroutines,
        storageUseCase
    )
}
