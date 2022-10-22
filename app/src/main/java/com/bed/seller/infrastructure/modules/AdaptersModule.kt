package com.bed.seller.infrastructure.modules

import io.ktor.client.HttpClient

import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

import com.bed.seller.data.client.AuthClient
import com.bed.seller.data.client.StorageClient
import com.bed.seller.data.client.ValidatorClient

import com.bed.seller.infrastructure.validator.adapters.ValidatorAdapter
import com.bed.seller.infrastructure.network.adapters.auth.AuthNetworkAdapter
import com.bed.seller.infrastructure.storage.adapters.StorageAdapter

fun adaptersModule() = module {
    single<AuthClient> {
        AuthNetworkAdapter(get<HttpClient>())
    }

    single<ValidatorClient> {
        ValidatorAdapter()
    }

    single<StorageClient> {
        StorageAdapter(androidContext())
    }
}
