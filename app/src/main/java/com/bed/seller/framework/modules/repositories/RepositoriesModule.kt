package com.bed.seller.framework.modules.repositories

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.core.repositories.CoroutinesRepository
import com.bed.core.repositories.AuthenticationRepository

import com.bed.seller.data.repositories.StorageRepository
import com.bed.seller.data.repositories.StorageRepositoryImpl

import com.bed.seller.data.repositories.CoroutinesRepositoryImpl
import com.bed.seller.data.repositories.AuthenticationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {
    @Binds
    fun bindStorageRepository(repository: StorageRepositoryImpl): StorageRepository

    @Binds
    @Singleton
    fun bindCoroutinesRepository(repository: CoroutinesRepositoryImpl): CoroutinesRepository

    @Binds
    fun bindAuthenticationRepository(repository: AuthenticationRepositoryImpl): AuthenticationRepository
}
