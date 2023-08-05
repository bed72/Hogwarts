package com.bed.seller.framework.network.adapters.mock

import kotlinx.serialization.json.Json
import kotlinx.serialization.ExperimentalSerializationApi

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.HttpClientConfig

import io.ktor.serialization.kotlinx.json.json

import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import com.bed.seller.BuildConfig

private const val TIMEOUT_MOCK = 15000L

@OptIn(ExperimentalSerializationApi::class)
private val adapterJsonMock get() = Json {
    explicitNulls = false
    encodeDefaults = false

    isLenient = true
    prettyPrint = true
    ignoreUnknownKeys = true
}

internal fun HttpClientConfig<*>.adapterLoggingMock() {
    install(Logging) {
        level = LogLevel.INFO
        level = LogLevel.HEADERS
    }
}

internal fun HttpClientConfig<*>.adapterRequestDefaultMock() {
    install(DefaultRequest) {
        url(BuildConfig.BASE_URL)
        headers {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("apikey", BuildConfig.API_KEY)
        }
    }
}

internal fun HttpClientConfig<*>.adapterResponseObserverMock() {
    install(ResponseObserver) {
        onResponse { response ->
            println("\n\n[KTOR HTTP STATUS MOCK]: ${response.status.value}\n\n")
            println("\n\n[KTOR HTTP RESPONSE MOCK]: ${response.body<String>()}\n\n")
        }
    }
}

internal fun HttpClientConfig<*>.adapterResponseTimeoutMock() {
    install(HttpTimeout) {
        socketTimeoutMillis = TIMEOUT_MOCK
        requestTimeoutMillis = TIMEOUT_MOCK
        connectTimeoutMillis = TIMEOUT_MOCK
    }
}

internal fun HttpClientConfig<*>.adapterContentNegotiationMock() {
    install(ContentNegotiation) {
        json(adapterJsonMock)
    }
}
