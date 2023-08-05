package com.bed.test.mocks

import io.ktor.http.headersOf
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.MockEngine

@Suppress("UtilityClassWithPublicConstructor")
class HttpAdapterMock {
    companion object {
        operator fun invoke(status: Int, content: String) = MockEngine {
            respond(
                content = content,
                status = statusCode(status),
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        private fun statusCode(status: Int): HttpStatusCode =
            when(status) {
                200 -> HttpStatusCode.OK
                else -> HttpStatusCode.InternalServerError
            }
    }
}
