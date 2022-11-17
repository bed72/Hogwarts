package com.bed.seller.presentation.ui.auth.refresh

import androidx.lifecycle.ViewModel

import com.bed.seller.domain.usecases.auth.RefreshUseCase
import com.bed.seller.domain.dispatchers.Coroutines

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.auth.refresh.states.RefreshLiveData

class RefreshViewModel(
    commons: Commons,
    coroutines: Coroutines,
    refreshUseCase: RefreshUseCase,
) : ViewModel() {

    val refresh = RefreshLiveData(
        commons,
        coroutines,
        refreshUseCase
    )
}
