package com.bed.seller.framework.network.interceptors

import okhttp3.Request
import okhttp3.Response
import okhttp3.Interceptor

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = if (verifyPath()) {
            val accessToken = getToken() ?: ""

            val response = chain.proceed(getNewRequest(request, accessToken))

            when (response.code) {
                HttpStatusCode.OK.value, HttpStatusCode.Created.value -> response
                HttpStatusCode.Unauthorized.value -> response
                else -> response
            }
        } else {
            chain.proceed(request)
        }

        return response
    }

    private fun getNewRequest(request: Request, data: String): Request = request.newBuilder()
        .header(HttpHeaders.Authorization, "Bearer $data")
        .build()

    @Suppress("FunctionOnlyReturningConstant")
    private fun verifyPath(): Boolean {
        return false
    }

    private fun getToken(): String? {
        val response = ""

        return response.ifEmpty { null }
    }
}
