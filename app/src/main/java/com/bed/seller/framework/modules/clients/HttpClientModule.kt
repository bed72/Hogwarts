package com.bed.seller.framework.modules.clients

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.seller.framework.network.clients.HttpClient
import com.bed.seller.framework.network.clients.HttpClientImpl

@Module
@InstallIn(SingletonComponent::class)
interface HttpClientModule {
    @Binds
    @Singleton
    fun bindHttpClient(client: HttpClientImpl): HttpClient
}
