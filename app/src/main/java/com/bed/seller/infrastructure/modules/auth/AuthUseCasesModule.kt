package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module

import com.bed.seller.data.client.SignUpClient
import com.bed.seller.data.client.StorageClient
import com.bed.seller.data.client.ValidatorClient

import com.bed.seller.domain.usecases.storage.StorageUseCase
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers
import com.bed.seller.domain.usecases.auth.signup.SignUpUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.data.usecases.auth.RemoteSignUpUseCase
import com.bed.seller.data.usecases.storage.LocalStorageUseCase
import com.bed.seller.data.usecases.validator.RemoteValidatorUseCase

val authUseCasesModule = module {
    single<StorageUseCase> {
        LocalStorageUseCase(get<StorageClient>())
    }

    single<ValidatorUseCase> {
        RemoteValidatorUseCase(get<ValidatorClient>())
    }

    single<SignUpUseCase> {
        RemoteSignUpUseCase(
            get<SignUpClient>(),
            get<CoroutinesDispatchers>(),
        )
    }
}
