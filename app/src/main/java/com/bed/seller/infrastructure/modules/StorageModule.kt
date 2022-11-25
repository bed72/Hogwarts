package com.bed.seller.infrastructure.modules

import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

import androidx.datastore.preferences.preferencesDataStore

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.usecases.storage.StorageUseCase

import com.bed.seller.data.client.storage.StorageClient
import com.bed.seller.data.client.security.SecurityClient
import com.bed.seller.data.usecases.storage.LocalStorageUseCase

import com.bed.seller.infrastructure.storage.StorageConstants
import com.bed.seller.infrastructure.storage.adapters.StorageAdapter

fun adapterStorageModule() = module {
    single<StorageClient> {
        StorageAdapter(
            get<SecurityClient>(),
            preferencesDataStore(
                name = StorageConstants.DATA_STORE_NAME
            ).getValue(androidContext(), String::javaClass)
        )
    }
}

fun storageUseCaseModule() = module {
    single<StorageUseCase> {
        LocalStorageUseCase(
            get<StorageClient>(),
            get<Coroutines>(),
        )
    }
}
