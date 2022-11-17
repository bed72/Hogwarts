package com.bed.seller.infrastructure.modules

import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

import com.bed.seller.data.client.storage.StorageClient

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.storage.GetStorageUseCase
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase

import com.bed.seller.data.usecases.storage.LocalGetStorageUseCase
import com.bed.seller.data.usecases.storage.LocalSaveStorageUseCase
import com.bed.seller.infrastructure.storage.adapters.StorageAdapter

fun adapterStorageModule() = module {
    single<StorageClient> {
        StorageAdapter(androidContext())
    }
}

fun storageUseCaseModule() = module {
    single<SaveStorageUseCase> {
        LocalSaveStorageUseCase(
            get<StorageClient>(),
            get<Coroutines>(),
        )
    }

    single<GetStorageUseCase> {
        LocalGetStorageUseCase(
            get<StorageClient>(),
            get<Coroutines>(),
        )
    }
}
