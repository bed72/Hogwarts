package com.bed.seller.framework.modules.clients

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.seller.framework.network.clients.FirebaseClient
import com.bed.seller.framework.network.clients.FirebaseClientImpl

@Module
@InstallIn(SingletonComponent::class)
interface ClientModule {
    @Binds
    @Singleton
    fun bindFirebaseClient(client: FirebaseClientImpl): FirebaseClient
}
