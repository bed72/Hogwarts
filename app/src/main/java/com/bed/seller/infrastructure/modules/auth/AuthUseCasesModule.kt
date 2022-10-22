package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module

import com.bed.seller.data.client.AuthClient
import com.bed.seller.data.client.StorageClient
import com.bed.seller.data.client.ValidatorClient

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.auth.AuthUseCase
import com.bed.seller.domain.usecases.storage.GetStorageUseCase
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.data.usecases.auth.RemoteSignUpUseCase
import com.bed.seller.data.usecases.storage.LocalGetStorageUseCase
import com.bed.seller.data.usecases.storage.LocalSaveStorageUseCase
import com.bed.seller.data.usecases.validator.RemoteValidatorUseCase

fun validatorsUSeCaseModule() = module {
    single<ValidatorUseCase> {
        RemoteValidatorUseCase(get<ValidatorClient>())
    }
}

fun storageUseCaseModule() = module {
    single<SaveStorageUseCase> {
        LocalSaveStorageUseCase(
            get<StorageClient>(),
            get<CoroutinesDispatchers>(),
        )
    }

    single<GetStorageUseCase> {
        LocalGetStorageUseCase(
            get<StorageClient>(),
            get<CoroutinesDispatchers>(),
        )
    }
}

val authUseCasesModule = module {
    single<AuthUseCase> {
        RemoteSignUpUseCase(
            get<AuthClient>(),
            get<CoroutinesDispatchers>(),
        )
    }
}
