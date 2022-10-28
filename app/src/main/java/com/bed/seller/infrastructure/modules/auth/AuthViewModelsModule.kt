package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.auth.AuthSignInUseCase
import com.bed.seller.domain.usecases.auth.AuthSignUpUseCase
import com.bed.seller.domain.usecases.auth.AuthRefreshUseCase
import com.bed.seller.domain.usecases.storage.GetStorageUseCase
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.auth.signin.SignInViewModel
import com.bed.seller.presentation.ui.auth.signup.SignUpViewModel
import com.bed.seller.presentation.ui.auth.tokens.TokensViewModel

val authViewModelsModule = module {

    viewModel {
        SignUpViewModel(
            get<Commons>(),
            get<ValidatorUseCase>(),
            get<AuthSignUpUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }

    viewModel {
        SignInViewModel(
            get<Commons>(),
            get<ValidatorUseCase>(),
            get<AuthSignInUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }

    viewModel {
        TokensViewModel(
            get<Commons>(),
            get<GetStorageUseCase>(),
            get<SaveStorageUseCase>(),
            get<AuthRefreshUseCase>(),
            get<CoroutinesDispatchers>(),
        )
    }
}
