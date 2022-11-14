package com.bed.seller.infrastructure.modules

import io.ktor.client.HttpClient

import org.koin.dsl.module

import com.bed.seller.data.client.auth.UserClient
import com.bed.seller.data.client.auth.SignInClient
import com.bed.seller.data.client.auth.SignUpClient
import com.bed.seller.data.client.auth.RefreshClient

import com.bed.seller.infrastructure.network.adapters.auth.UserAdapter
import com.bed.seller.infrastructure.network.adapters.auth.SignInAdapter
import com.bed.seller.infrastructure.network.adapters.auth.SignUpAdapter
import com.bed.seller.infrastructure.network.adapters.auth.RefreshAdapter

fun adapterClientModule() = module {
    single<RefreshClient> {
        RefreshAdapter(get<HttpClient>())
    }

    single<SignInClient> {
        SignInAdapter(get<HttpClient>())
    }

    single<SignUpClient> {
        SignUpAdapter(get<HttpClient>())
    }

    single<UserClient> {
        UserAdapter(get<HttpClient>())
    }
}
