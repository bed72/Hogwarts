package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.auth.AuthUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.presentation.ui.auth.commons.Auth
import com.bed.seller.presentation.ui.auth.signup.SignUpViewModel

val authViewModelsModule = module {
    viewModel {
        SignUpViewModel(
            get<Auth>(),
            get<AuthUseCase>(),
            get<ValidatorUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }
}
