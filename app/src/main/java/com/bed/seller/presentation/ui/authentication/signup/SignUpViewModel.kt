package com.bed.seller.presentation.ui.authentication.signup

import androidx.lifecycle.ViewModel
import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase
import com.bed.seller.presentation.ui.authentication.signup.states.SignUpLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    signUpUseCase: SignUpUseCase,
    coroutinesUseCase: CoroutinesUseCase,
) : ViewModel() {

    val auth = SignUpLiveData(signUpUseCase, coroutinesUseCase)
}
