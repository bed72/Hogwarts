package com.bed.seller.presentation.ui.authentication.signup

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.core.usecases.storage.SaveStorageUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.seller.presentation.ui.authentication.signup.states.SignUpLiveData
import com.bed.seller.presentation.ui.authentication.signup.states.SignUpSaveTokensLiveData

@HiltViewModel
class SignUpViewModel @Inject constructor(
    signUpUseCase: SignUpUseCase,
    coroutinesUseCase: CoroutinesUseCase,
    saveStorageUseCase: SaveStorageUseCase
) : ViewModel() {

    val auth = SignUpLiveData(signUpUseCase, coroutinesUseCase)
    val tokens = SignUpSaveTokensLiveData(coroutinesUseCase, saveStorageUseCase)
}
