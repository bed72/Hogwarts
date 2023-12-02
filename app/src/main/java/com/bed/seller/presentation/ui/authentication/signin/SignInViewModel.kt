package com.bed.seller.presentation.ui.authentication.signin

import javax.inject.Inject

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.MutableLiveData

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.seller.presentation.commons.states.EmailState
import com.bed.seller.presentation.commons.states.StorageState
import com.bed.seller.presentation.commons.states.PasswordState

import com.bed.core.usecases.storage.SaveStorageUseCase
import com.bed.core.usecases.authentication.SignInUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.domain.models.authentication.AuthenticationModel
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

@HiltViewModel
class SignInViewModel @Inject constructor(
    signInUseCase: SignInUseCase,
    coroutinesUseCase: CoroutinesUseCase,
    saveStorageUseCase: SaveStorageUseCase
) : ViewModel() {

    private val storage = StorageState(saveStorageUseCase)

    val email = EmailState(coroutinesUseCase)
    val password = PasswordState(coroutinesUseCase)

    private val actions = MutableLiveData<Actions>()

    val states: LiveData<States> = actions.switchMap { action ->
        liveData(coroutinesUseCase.main()) {
            if (action is Actions.SignIn) {
                emit(States.Loading)

                signInUseCase(action.parameter).collect { data ->
                    data.fold(
                        { failure -> emit(States.Failure(failure.message)) },
                        { success ->
                            storage.save(success)
                            emit(States.Success(success))
                        }
                    )
                }
            }
        }
    }

    fun signIn(parameter: AuthenticationParameter) {
        actions.value = Actions.SignIn(parameter)
    }

    sealed class Actions {
        data class SignIn(val parameter: AuthenticationParameter) : Actions()
    }

    sealed class States {
        data object Loading : States()
        data class Failure(val data: String) : States()
        data class Success(val data: AuthenticationModel) : States()
    }
}
