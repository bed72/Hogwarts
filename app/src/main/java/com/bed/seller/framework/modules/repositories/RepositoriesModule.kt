package com.bed.seller.framework.modules.repositories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.core.data.repositories.storage.StorageRepository
import com.bed.core.data.repositories.storage.StorageRepositoryImpl

import com.bed.core.data.repositories.authentication.AuthenticationRepository
import com.bed.core.data.repositories.authentication.AuthenticationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {
    @Binds
    fun bindStorageRepository(repository: StorageRepositoryImpl): StorageRepository

    @Binds
    fun bindAuthenticationRepository(repository: AuthenticationRepositoryImpl): AuthenticationRepository
}
