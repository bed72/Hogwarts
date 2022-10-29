package com.bed.seller.infrastructure.modules

import org.koin.dsl.module

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor

import com.bed.seller.infrastructure.network.interceptors.AuthInterceptor

import com.bed.seller.infrastructure.configuration.installLogging
import com.bed.seller.infrastructure.configuration.installRequestDefault
import com.bed.seller.infrastructure.configuration.installResponseTimeout
import com.bed.seller.infrastructure.configuration.installResponseObserver
import com.bed.seller.infrastructure.configuration.installContentNegotiation

fun interceptorsModule() = module {
    factory {
        AuthInterceptor()
    }

    factory {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
}

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
                    protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
                }

                addInterceptor(get<AuthInterceptor>())

                addInterceptor(get<HttpLoggingInterceptor>())
            }
        }
    }
}
