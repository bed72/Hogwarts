package com.bed.seller.presentation.ui.splash

import androidx.lifecycle.ViewModel
import com.bed.core.usecases.coroutines.CoroutinesUseCase
import com.bed.core.usecases.storage.GetStorageUseCase
import com.bed.seller.presentation.ui.splash.states.SplashGetTokenLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    coroutinesUseCase: CoroutinesUseCase,
    getStorageUseCase: GetStorageUseCase,
) : ViewModel() {
    val get = SplashGetTokenLiveData(coroutinesUseCase, getStorageUseCase)
}
