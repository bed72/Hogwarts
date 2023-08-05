package com.bed.seller.framework.network.clients

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import javax.inject.Inject

import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor

import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

import io.ktor.client.HttpClientConfig
import io.ktor.client.HttpClient as KtorClient

import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig

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

interface HttpClient {
    val ktor: KtorClient
}

class HttpClientImpl @Inject constructor() : HttpClient {

    private val timeout = 15000L

    @OptIn(ExperimentalSerializationApi::class)
    private val configureJson get() = Json {
        explicitNulls = false
        encodeDefaults = false

        isLenient = true
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    override val ktor get() = KtorClient(OkHttp) {
        configureLogging()
        configureRequestDefault()
        configureResponseTimeout()
        configureResponseObserver()
        configureContentNegotiation()
    }

    private fun HttpClientConfig<OkHttpConfig>.configureLogging() {
        install(Logging) {
            level = LogLevel.INFO
            level = LogLevel.HEADERS
        }
    }

    private fun HttpClientConfig<OkHttpConfig>.configureRequestDefault() {
        install(DefaultRequest) {
            url(BuildConfig.BASE_URL)
            headers {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("apikey", BuildConfig.API_KEY)
            }
        }
    }

    private fun HttpClientConfig<OkHttpConfig>.configureResponseObserver() {
        install(ResponseObserver) {
            onResponse { response ->
                println("\n\n[KTOR HTTP STATUS]: ${response.status.value}\n\n")
                println("\n\n[KTOR HTTP RESPONSE]: ${response.body<String>()}\n\n")
            }
        }
    }

    private fun HttpClientConfig<OkHttpConfig>.configureResponseTimeout() {
        install(HttpTimeout) {
            socketTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            connectTimeoutMillis = timeout
        }
    }

    private fun HttpClientConfig<OkHttpConfig>.configureContentNegotiation() {
        install(ContentNegotiation) {
            json(configureJson)

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
}

suspend inline fun <reified F : Any, reified S : Any> KtorClient.request(
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
