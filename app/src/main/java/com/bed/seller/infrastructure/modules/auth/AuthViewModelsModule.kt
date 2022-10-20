package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.auth.AuthUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.presentation.ui.auth.commons.Auth
import com.bed.seller.presentation.ui.auth.signin.SignInViewModel
import com.bed.seller.presentation.ui.auth.signup.SignUpViewModel
import com.bed.seller.presentation.ui.auth.tokens.TokensViewModel

val authViewModelsModule = module {
    viewModel {
        TokensViewModel(
            get<Auth>(),
            get<AuthUseCase>(),
            get<CoroutinesDispatchers>(),
        )
    }

    viewModel {
        SignUpViewModel(
            get<Auth>(),
            get<AuthUseCase>(),
            get<ValidatorUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }

    viewModel {
        SignInViewModel(
            get<Auth>(),
            get<AuthUseCase>(),
            get<ValidatorUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }
}
