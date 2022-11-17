package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module

import com.bed.seller.data.client.auth.UserClient
import com.bed.seller.data.client.auth.SignUpClient
import com.bed.seller.data.client.auth.SignInClient
import com.bed.seller.data.client.auth.RefreshClient
import com.bed.seller.data.client.storage.StorageClient

import com.bed.seller.data.usecases.auth.RemoteUserUseCase
import com.bed.seller.data.usecases.auth.RemoteSignInUseCase
import com.bed.seller.data.usecases.auth.RemoteSignUpUseCase
import com.bed.seller.data.usecases.auth.RemoteRefreshUseCase

import com.bed.seller.domain.usecases.auth.UserUseCase
import com.bed.seller.domain.usecases.auth.SignUpUseCase
import com.bed.seller.domain.usecases.auth.SignInUseCase
import com.bed.seller.domain.usecases.auth.RefreshUseCase
import com.bed.seller.domain.dispatchers.Coroutines

val refreshUseCasesModule = module {
    single<RefreshUseCase> {
        RemoteRefreshUseCase(
            get<StorageClient>(),
            get<RefreshClient>(),
            get<Coroutines>(),
        )
    }
}

val signInUseCasesModule = module {
    single<SignInUseCase> {
        RemoteSignInUseCase(
            get<StorageClient>(),
            get<SignInClient>(),
            get<Coroutines>(),
        )
    }
}

val signUpUseCasesModule = module {
    single<SignUpUseCase> {
        RemoteSignUpUseCase(
            get<StorageClient>(),
            get<SignUpClient>(),
            get<Coroutines>(),
        )
    }
}

val getUserUseCaseModule = module {
    single<UserUseCase> {
        RemoteUserUseCase(
            get<UserClient>(),
            get<Coroutines>(),
        )
    }
}
