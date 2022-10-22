package com.bed.seller.infrastructure.network.interceptors

import okhttp3.Response
import okhttp3.Interceptor

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString

import com.bed.seller.domain.usecases.storage.SaveStorageUseCase

import com.bed.seller.infrastructure.storage.StorageConstants
import com.bed.seller.infrastructure.configuration.JSON_CONFIG
import com.bed.seller.infrastructure.network.models.auth.AuthResponseModel

class AuthInterceptor(
    private val saveStorageUseCase: SaveStorageUseCase
) : Interceptor {
    private val json = JSON_CONFIG

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(chain.request())
        val url = request.url


        /**
         * Save data token in Storage
         */
        if (url.pathSegments.contains("/auth/v1/").not()) {
            val body = toModel<AuthResponseModel>(response)

            saveInStorage(
                StorageConstants.DATA_STORE_ACCESS_TOKEN to body.accessToken,
                StorageConstants.DATA_STORE_REFRESH_TOKEN to body.refreshToken,
                StorageConstants.DATA_STORE_EXPIRES_IN to body.expiresIn.toString()
            )
        }

        return chain.proceed(
            request.newBuilder()
                .url(url)
                .build()
        )
    }

    private inline fun <reified T> toModel(response: Response): T {
        val body = response.peekBody(Long.MAX_VALUE)

        return  json.decodeFromString<T>(body.string())
    }

    private fun saveInStorage(vararg data: Pair<String, String>) = runBlocking {
        for (value in data) saveStorageUseCase(value.first to value.second)
    }

}
