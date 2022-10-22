package com.bed.seller.infrastructure.modules

import com.bed.seller.domain.usecases.storage.SaveStorageUseCase
import org.koin.dsl.module

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

import com.bed.seller.infrastructure.network.interceptors.AuthInterceptor

import com.bed.seller.infrastructure.configuration.installLogging
import com.bed.seller.infrastructure.configuration.installRequestDefault
import com.bed.seller.infrastructure.configuration.installResponseTimeout
import com.bed.seller.infrastructure.configuration.installResponseObserver
import com.bed.seller.infrastructure.configuration.installContentNegotiation

fun networkModule() = module {
    single {
        HttpClient(OkHttp) {
            installLogging()
            installRequestDefault()
            installResponseTimeout()
            installResponseObserver()
            installContentNegotiation()

            engine {
                config {
                    followRedirects(false)
                }

                addInterceptor(AuthInterceptor(get<SaveStorageUseCase>()))
            }
        }
    }
}
