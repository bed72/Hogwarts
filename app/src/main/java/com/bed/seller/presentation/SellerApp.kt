package com.bed.seller.presentation

import android.app.Application

import com.bed.seller.BuildConfig

import org.koin.core.logger.Level
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.koin.androidContext

import com.bed.seller.infrastructure.modules.networkModule
import com.bed.seller.infrastructure.modules.dispatchersModule
import com.bed.seller.infrastructure.modules.adapterClientModule
import com.bed.seller.infrastructure.modules.adapterStorageModule
import com.bed.seller.infrastructure.modules.auth.authCommonModule
import com.bed.seller.infrastructure.modules.adapterValidatorModule
import com.bed.seller.infrastructure.modules.auth.authRefreshUseCasesModule
import com.bed.seller.infrastructure.modules.auth.authViewModelsModule
import com.bed.seller.infrastructure.modules.auth.storageUseCaseModule
import com.bed.seller.infrastructure.modules.auth.validatorsUSeCaseModule
import com.bed.seller.infrastructure.modules.home.homeViewModelsModule

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
                // Adapters
                adapterClientModule(),
                adapterStorageModule(),
                adapterValidatorModule(),

                // UseCases
                storageUseCaseModule(),
                validatorsUSeCaseModule(),

                // Auth Modules
                authCommonModule(),
                authRefreshUseCasesModule,
                authViewModelsModule,
                // Home Modules
                homeViewModelsModule
            )
        }
    }
}
