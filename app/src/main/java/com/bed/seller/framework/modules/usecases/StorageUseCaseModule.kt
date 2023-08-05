package com.bed.seller.framework.modules.usecases

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import com.bed.core.usecases.storage.GetStorageUseCase
import com.bed.core.usecases.storage.GetStorageUseCaseImpl

import com.bed.core.usecases.storage.SaveStorageUseCase
import com.bed.core.usecases.storage.SaveStorageUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface StorageUseCaseModule {

    @Binds
    fun bindGetStorageUseCase(useCase: GetStorageUseCaseImpl): GetStorageUseCase

    @Binds
    fun bindSaveStorageUseCase(useCase: SaveStorageUseCaseImpl): SaveStorageUseCase
}
