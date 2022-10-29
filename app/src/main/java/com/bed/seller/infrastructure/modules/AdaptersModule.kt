package com.bed.seller.infrastructure.modules

import io.ktor.client.HttpClient

import org.koin.dsl.module

import com.bed.seller.data.client.AuthSignInClient
import com.bed.seller.data.client.AuthSignUpClient
import com.bed.seller.data.client.AuthRefreshClient

import com.bed.seller.infrastructure.network.adapters.auth.AuthRefreshNetworkAdapter
import com.bed.seller.infrastructure.network.adapters.auth.AuthSignInNetworkAdapter
import com.bed.seller.infrastructure.network.adapters.auth.AuthSignUpNetworkAdapter

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
