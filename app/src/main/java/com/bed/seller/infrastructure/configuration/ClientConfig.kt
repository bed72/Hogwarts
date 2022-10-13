package com.bed.seller.infrastructure.configuration

import android.util.Log

import com.bed.seller.BuildConfig

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig

import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import kotlinx.serialization.json.Json

private const val TIMEOUT_MILLIS = 15000L

fun HttpClientConfig<OkHttpConfig>.installLogging() {
    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("[KTOR CLIENT]: ", message)
            }
        }
    }
}

fun HttpClientConfig<OkHttpConfig>.installRequestDefault() {
    install(DefaultRequest) {
        headers {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("apikey", BuildConfig.API_KEY)
        }
    }
}

fun HttpClientConfig<OkHttpConfig>.installResponseObserver() {
    install(ResponseObserver) {
        onResponse { response ->
            Log.d("[KTOR HTTP RESPONSE]: ", response.body())
            Log.d("[KTOR HTTP STATUS]: ", response.status.value.toString())
        }
    }
}

fun HttpClientConfig<OkHttpConfig>.installResponseTimeout() {
    install(HttpTimeout) {
        socketTimeoutMillis = TIMEOUT_MILLIS
        requestTimeoutMillis = TIMEOUT_MILLIS
        connectTimeoutMillis = TIMEOUT_MILLIS
    }
}

fun HttpClientConfig<OkHttpConfig>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(
            Json {
                isLenient = true
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )

        engine {
            config {
                followRedirects(false)
            }
        }
    }
}




