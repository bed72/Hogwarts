package com.bed.seller.presentation.ui.splash

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.storage.GetStorageUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.seller.presentation.ui.splash.states.SplashGetTokenLiveData

@HiltViewModel
class SplashViewModel @Inject constructor(
    coroutinesUseCase: CoroutinesUseCase,
    getStorageUseCase: GetStorageUseCase,
) : ViewModel() {
    val get = SplashGetTokenLiveData(coroutinesUseCase, getStorageUseCase)
}
