package com.bed.seller.presentation

import android.app.Application

import com.bed.seller.BuildConfig

import org.koin.core.logger.Level
import org.koin.core.context.startKoin

import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.koin.androidContext

import com.bed.seller.infrastructure.modules.networkModule
import com.bed.seller.infrastructure.modules.dispatchersModule
import com.bed.seller.infrastructure.modules.interceptorsModule
import com.bed.seller.infrastructure.modules.adapterClientModule
import com.bed.seller.infrastructure.modules.adapterSecurityModule
import com.bed.seller.infrastructure.modules.adapterStorageModule
import com.bed.seller.infrastructure.modules.adapterValidatorModule

import com.bed.seller.infrastructure.modules.storageUseCaseModule
import com.bed.seller.infrastructure.modules.validatorsUseCaseModule

import com.bed.seller.infrastructure.modules.auth.authCommonModule
import com.bed.seller.infrastructure.modules.auth.authViewModelsModule
import com.bed.seller.infrastructure.modules.auth.getUserUseCaseModule
import com.bed.seller.infrastructure.modules.auth.signInUseCasesModule
import com.bed.seller.infrastructure.modules.auth.signUpUseCasesModule
import com.bed.seller.infrastructure.modules.home.homeViewModelsModule
import com.bed.seller.infrastructure.modules.auth.refreshUseCasesModule

class SellerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SellerApp)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)

            modules(
                // Shared Modules
                networkModule(),
                dispatchersModule(),
                interceptorsModule(),

                // Adapters
                adapterClientModule(),
                adapterStorageModule(),
                adapterSecurityModule(),
                adapterValidatorModule(),

                // Shared UseCases
                storageUseCaseModule(),
                validatorsUseCaseModule(),

                // Auth UseCases
                getUserUseCaseModule,
                signInUseCasesModule,
                signUpUseCasesModule,
                refreshUseCasesModule,

                // Shared Auth Modules
                authCommonModule(),

                // Auth ViewModel Modules
                authViewModelsModule,

                // Home Modules
                homeViewModelsModule
            )
        }
    }
}
