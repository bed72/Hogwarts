package com.bed.seller.framework.network.adapters.mock

import io.ktor.http.headersOf
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.MockEngine

import com.bed.seller.framework.network.adapters.HttpAdapter

interface HttpAdapterMock : HttpAdapter {
    operator fun invoke(content: String, status: HttpStatusCode): MockEngine
}

class HttpAdapterMockImpl : HttpAdapterMock {

    lateinit var content: String

    lateinit var status: HttpStatusCode

    override fun invoke(content: String, status: HttpStatusCode) = MockEngine {
        respond(
            status = status,
            content = content,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    override val ktor: HttpClient
        get() = HttpClient(engine = invoke(content, status)) {
            adapterLoggingMock()
            adapterRequestDefaultMock()
            adapterResponseTimeoutMock()
            adapterResponseObserverMock()
            adapterContentNegotiationMock()
        }

    companion object {
        operator fun invoke() = HttpAdapterMockImpl()
    }
}
