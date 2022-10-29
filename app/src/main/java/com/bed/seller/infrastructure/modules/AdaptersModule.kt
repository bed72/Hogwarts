package com.bed.seller.infrastructure.modules

import io.ktor.client.HttpClient

import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

import com.bed.seller.data.client.AuthRefreshClient
import com.bed.seller.data.client.AuthSignInClient
import com.bed.seller.data.client.AuthSignUpClient
import com.bed.seller.data.client.StorageClient
import com.bed.seller.data.client.ValidatorClient

import com.bed.seller.infrastructure.validator.adapters.ValidatorAdapter
import com.bed.seller.infrastructure.network.adapters.auth.AuthRefreshNetworkAdapter
import com.bed.seller.infrastructure.network.adapters.auth.AuthSignInNetworkAdapter
import com.bed.seller.infrastructure.network.adapters.auth.AuthSignUpNetworkAdapter
import com.bed.seller.infrastructure.storage.adapters.StorageAdapter

fun adapterClientModule() = module {
    single<AuthRefreshClient> {
        AuthRefreshNetworkAdapter(get<HttpClient>())
    }

    single<AuthSignInClient> {
        AuthSignInNetworkAdapter(get<HttpClient>())
    }

    single<AuthSignUpClient> {
        AuthSignUpNetworkAdapter(get<HttpClient>())
    }
}

fun adapterStorageModule() = module {
    single<StorageClient> {
        StorageAdapter(androidContext())
    }
}

fun adapterValidatorModule() = module {
    single<ValidatorClient> {
        ValidatorAdapter()
    }
}
