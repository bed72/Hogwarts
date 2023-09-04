package com.bed.seller.presentation.ui.splash

import javax.inject.Inject

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.authentication.IsLoggedInUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val coroutinesUseCase: CoroutinesUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
) : ViewModel() {
    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions.switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            emit(States.Loading)

            if (action is Actions.IsLoggedIn)
                isLoggedInUseCase().collect { emit(States.IsLoggedIn(it)) }
        }
    }

    fun isLoggedIn() {
        actions.value = Actions.IsLoggedIn
    }

    sealed class Actions {
        data object IsLoggedIn : Actions()
    }

    sealed class States {
        data object Loading : States()
        data class IsLoggedIn(val isSuccess: Boolean) : States()
    }
}
