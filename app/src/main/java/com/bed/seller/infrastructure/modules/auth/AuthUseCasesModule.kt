package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module

import com.bed.seller.data.client.StorageClient
import com.bed.seller.data.client.ValidatorClient
import com.bed.seller.data.client.AuthSignInClient
import com.bed.seller.data.client.AuthSignUpClient
import com.bed.seller.data.client.AuthRefreshClient

import com.bed.seller.domain.usecases.auth.AuthSignUpUseCase
import com.bed.seller.domain.usecases.auth.AuthSignInUseCase
import com.bed.seller.data.usecases.auth.RemoteSignInUseCase
import com.bed.seller.data.usecases.auth.RemoteSignUpUseCase
import com.bed.seller.data.usecases.auth.RemoteRefreshUseCase

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

import com.bed.seller.domain.usecases.auth.AuthRefreshUseCase
import com.bed.seller.domain.usecases.storage.GetStorageUseCase
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

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

val authRefreshUseCasesModule = module {
    single<AuthRefreshUseCase> {
        RemoteRefreshUseCase(
            get<AuthRefreshClient>(),
            get<CoroutinesDispatchers>(),
        )
    }

    single<AuthSignInUseCase> {
        RemoteSignInUseCase(
            get<AuthSignInClient>(),
            get<CoroutinesDispatchers>(),
        )
    }

    single<AuthSignUpUseCase> {
        RemoteSignUpUseCase(
            get<AuthSignUpClient>(),
            get<CoroutinesDispatchers>(),
        )
    }
}
