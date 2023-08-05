package com.bed.seller.framework.network.adapters

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor

import kotlinx.serialization.json.Json
import kotlinx.serialization.ExperimentalSerializationApi

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

import io.ktor.serialization.kotlinx.json.json

import io.ktor.client.statement.HttpResponse
import io.ktor.client.engine.okhttp.OkHttpConfig

import io.ktor.client.call.body
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.HttpRequestBuilder

import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import com.bed.seller.BuildConfig

private const val TIMEOUT = 15000L

@OptIn(ExperimentalSerializationApi::class)
private val adapterJson get() = Json {
    explicitNulls = false
    encodeDefaults = false

    isLenient = true
    prettyPrint = true
    ignoreUnknownKeys = true
}

internal fun HttpClientConfig<OkHttpConfig>.adapterLogging() {
    install(Logging) {
        level = LogLevel.INFO
        level = LogLevel.HEADERS
    }
}

internal fun HttpClientConfig<OkHttpConfig>.adapterRequestDefault() {
    install(DefaultRequest) {
        url(BuildConfig.BASE_URL)
        headers {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("apikey", BuildConfig.API_KEY)
        }
    }
}

internal fun HttpClientConfig<OkHttpConfig>.adapterResponseObserver() {
    install(ResponseObserver) {
        onResponse { response ->
            println("\n\n[KTOR HTTP STATUS]: ${response.status.value}\n\n")
            println("\n\n[KTOR HTTP RESPONSE]: ${response.body<String>()}\n\n")
        }
    }
}

internal fun HttpClientConfig<OkHttpConfig>.adapterResponseTimeout() {
    install(HttpTimeout) {
        socketTimeoutMillis = TIMEOUT
        requestTimeoutMillis = TIMEOUT
        connectTimeoutMillis = TIMEOUT
    }
}

internal fun HttpClientConfig<OkHttpConfig>.adapterContentNegotiation() {
    install(ContentNegotiation) {
        json(adapterJson)

        engine {
            config {
                followRedirects(false)
                protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
            }

            addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
        }
    }
}

suspend inline fun <reified F : Any, reified S : Any> HttpClient.request(
    block: HttpRequestBuilder.() -> Unit,
): Either<F, S> {
    val response = request { block() }

    close()

    return when (response.status) {
        HttpStatusCode.OK, HttpStatusCode.Created -> success(response)
        else -> failure(response)
    }
}

suspend inline fun <reified F> failure(response: HttpResponse) = response.body<F>().left()

suspend inline fun <reified S> success(response: HttpResponse) = response.body<S>().right()
