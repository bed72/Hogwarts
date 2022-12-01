package com.bed.seller.presentation.ui.auth.user

import androidx.lifecycle.ViewModel

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.auth.UserUseCase
import com.bed.seller.domain.usecases.storage.StorageUseCase
import com.bed.seller.presentation.ui.auth.user.states.UserLiveData

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.splash.states.SplashLiveData

class UserViewModel(
    commons: Commons,
    coroutines: Coroutines,
    userUseCase: UserUseCase,
    storageUseCase: StorageUseCase
) : ViewModel() {

    val user = UserLiveData(
        commons,
        coroutines,
        userUseCase,
        storageUseCase
    )
}
