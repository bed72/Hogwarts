package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module

import com.bed.seller.data.client.StorageClient
import com.bed.seller.data.client.AuthSignInClient
import com.bed.seller.data.client.AuthSignUpClient
import com.bed.seller.data.client.AuthRefreshClient

import com.bed.seller.domain.usecases.auth.AuthSignUpUseCase
import com.bed.seller.domain.usecases.auth.AuthSignInUseCase
import com.bed.seller.data.usecases.auth.RemoteSignInUseCase
import com.bed.seller.data.usecases.auth.RemoteSignUpUseCase
import com.bed.seller.data.usecases.auth.RemoteRefreshUseCase

import com.bed.seller.domain.usecases.auth.AuthRefreshUseCase
import com.bed.seller.domain.dispatchers.CoroutinesDispatchers

val authRefreshUseCasesModule = module {
    single<AuthRefreshUseCase> {
        RemoteRefreshUseCase(
            get<StorageClient>(),
            get<AuthRefreshClient>(),
            get<CoroutinesDispatchers>(),
        )
    }
}

val signInUseCasesModule = module {
    single<AuthSignInUseCase> {
        RemoteSignInUseCase(
            get<StorageClient>(),
            get<AuthSignInClient>(),
            get<CoroutinesDispatchers>(),
        )
    }
}

val signUpUseCasesModule = module {
    single<AuthSignUpUseCase> {
        RemoteSignUpUseCase(
            get<StorageClient>(),
            get<AuthSignUpClient>(),
            get<CoroutinesDispatchers>(),
        )
    }
}
