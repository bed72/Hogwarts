package com.bed.seller.presentation.ui.splash

import javax.inject.Inject

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.authentication.VerifyUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val verifyUseCase: VerifyUseCase,
    private val coroutinesUseCase: CoroutinesUseCase,
) : ViewModel() {
    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions.switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            if (action is Actions.IsLoggedIn) {
                verifyUseCase().collect { data ->
                    if (data) emit(States.Success) else emit(States.Failure)
                }
            }
        }
    }

    fun isLoggedIn() {
        actions.value = Actions.IsLoggedIn
    }

    sealed class Actions {
        data object IsLoggedIn : Actions()
    }

    sealed class States {
        data object Failure : States()
        data object Success : States()
    }
}
