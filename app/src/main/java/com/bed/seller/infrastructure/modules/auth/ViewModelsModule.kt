package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.auth.RefreshUseCase

import com.bed.seller.domain.usecases.auth.UserUseCase
import com.bed.seller.domain.usecases.auth.SignInUseCase
import com.bed.seller.domain.usecases.auth.SignUpUseCase
import com.bed.seller.domain.usecases.storage.GetStorageUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.auth.user.UserViewModel
import com.bed.seller.presentation.ui.auth.signin.SignInViewModel
import com.bed.seller.presentation.ui.auth.signup.SignUpViewModel
import com.bed.seller.presentation.ui.auth.refresh.RefreshViewModel

val authViewModelsModule = module {

    viewModel {
        SignUpViewModel(
            get<Commons>(),
            get<Coroutines>(),
            get<SignUpUseCase>(),
            get<ValidatorUseCase>()
        )
    }

    viewModel {
        SignInViewModel(
            get<Commons>(),
            get<Coroutines>(),
            get<SignInUseCase>(),
            get<ValidatorUseCase>()
        )
    }

    viewModel {
        RefreshViewModel(
            get<Commons>(),
            get<Coroutines>(),
            get<RefreshUseCase>()
        )
    }

    viewModel {
        UserViewModel(
            get<Commons>(),
            get<Coroutines>(),
            get<UserUseCase>(),
            get<GetStorageUseCase>()
        )
    }
}
