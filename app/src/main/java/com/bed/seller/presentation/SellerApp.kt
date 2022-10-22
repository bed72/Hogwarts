package com.bed.seller.presentation

import android.app.Application

import com.bed.seller.BuildConfig

import org.koin.core.logger.Level
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.koin.androidContext

import com.bed.seller.infrastructure.modules.networkModule
import com.bed.seller.infrastructure.modules.adaptersModule
import com.bed.seller.infrastructure.modules.dispatchersModule
import com.bed.seller.infrastructure.modules.auth.authCommonModule
import com.bed.seller.infrastructure.modules.auth.authUseCasesModule
import com.bed.seller.infrastructure.modules.auth.authViewModelsModule
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
                adaptersModule(),
                dispatchersModule(),
                // Auth Modules
                authCommonModule(),
                authUseCasesModule,
                authViewModelsModule,
                // Home Modules
                homeViewModelsModule
            )
        }
    }
}
