package com.bed.seller.framework.network.clients.mock

import kotlinx.serialization.json.Json
import kotlinx.serialization.ExperimentalSerializationApi

import io.ktor.http.headersOf
import io.ktor.http.HttpHeaders
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode

import io.ktor.serialization.kotlinx.json.json

import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.headers

import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.MockEngine

import io.ktor.client.HttpClientConfig
import io.ktor.client.HttpClient as KtorClient

import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import com.bed.seller.BuildConfig

interface HttpClientMock : com.bed.seller.framework.network.clients.HttpClient {
    operator fun invoke(content: String, status: HttpStatusCode): MockEngine
}

class HttpClientMockImpl : HttpClientMock {

    private val timeout = 15000L

    lateinit var content: String

    lateinit var status: HttpStatusCode

    override fun invoke(content: String, status: HttpStatusCode) = MockEngine {
        respond(
            status = status,
            content = content,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val adapterJsonMock get() = Json {
        explicitNulls = false
        encodeDefaults = false

        isLenient = true
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    override val ktor: KtorClient
        get() = KtorClient(engine = invoke(content, status)) {
            configureLoggingMock()
            configureRequestDefaultMock()
            configureResponseTimeoutMock()
            configureResponseObserverMock()
            configureContentNegotiationMock()
        }

    private fun HttpClientConfig<*>.configureLoggingMock() {
        install(Logging) {
            level = LogLevel.INFO
            level = LogLevel.HEADERS
        }
    }

    private fun HttpClientConfig<*>.configureRequestDefaultMock() {
        install(DefaultRequest) {
            url(BuildConfig.BASE_URL)
            headers {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("apikey", BuildConfig.API_KEY)
            }
        }
    }

    private fun HttpClientConfig<*>.configureResponseObserverMock() {
        install(ResponseObserver) {
            onResponse { response ->
                println("\n\n[KTOR HTTP STATUS MOCK]: ${response.status.value}\n\n")
                println("\n\n[KTOR HTTP RESPONSE MOCK]: ${response.body<String>()}\n\n")
            }
        }
    }

    private fun HttpClientConfig<*>.configureResponseTimeoutMock() {
        install(HttpTimeout) {
            socketTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            connectTimeoutMillis = timeout
        }
    }

    private fun HttpClientConfig<*>.configureContentNegotiationMock() {
        install(ContentNegotiation) {
            json(adapterJsonMock)
        }
    }

    companion object {
        operator fun invoke() = HttpClientMockImpl()
    }
}
