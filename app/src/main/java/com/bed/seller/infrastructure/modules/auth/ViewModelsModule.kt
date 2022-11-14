package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.auth.UserUseCase
import com.bed.seller.domain.usecases.auth.SignInUseCase
import com.bed.seller.domain.usecases.auth.SignUpUseCase
import com.bed.seller.domain.usecases.storage.GetStorageUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.auth.user.UserViewModel
import com.bed.seller.presentation.ui.auth.signin.SignInViewModel
import com.bed.seller.presentation.ui.auth.signup.SignUpViewModel
import com.bed.seller.presentation.ui.auth.tokens.TokensViewModel

val authViewModelsModule = module {

    viewModel {
        SignUpViewModel(
            get<Commons>(),
            get<ValidatorUseCase>(),
            get<SignUpUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }

    viewModel {
        SignInViewModel(
            get<Commons>(),
            get<ValidatorUseCase>(),
            get<SignInUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }

    viewModel {
        TokensViewModel(
            get<GetStorageUseCase>(),
            get<CoroutinesDispatchers>(),
        )
    }

    viewModel {
        UserViewModel(
            get<Commons>(),
            get<UserUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }
}
