package com.bed.seller.infrastructure.network.interceptors

import arrow.core.Either

import okhttp3.Request
import okhttp3.Response
import okhttp3.Interceptor

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

import com.bed.seller.infrastructure.storage.StorageConstants

import com.bed.seller.domain.usecases.auth.RefreshUseCase
import com.bed.seller.domain.usecases.storage.StorageUseCase

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.auth.tokens.RefreshTokenBodyRequestEntity

class AuthInterceptor : Interceptor, KoinComponent {

    private val refreshUseCase: RefreshUseCase by inject()
    private val storageUseCase: StorageUseCase by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = if (verifyPath(request.url.toString())) {
            val accessToken = getToken() ?: ""
            val refreshToken = getToken(false) ?: ""

            val response = chain.proceed(getNewRequest(request, accessToken))

            when (response.code) {
                HttpStatusCode.OK.value, HttpStatusCode.Created.value -> response
                HttpStatusCode.Unauthorized.value -> {
                    val data = refresh(refreshToken)

                    if (data == null) response
                    else chain.proceed(getNewRequest(request, data.refreshToken))
                }
                else -> response
            }
        } else chain.proceed(request)

        return response
    }

    private fun getNewRequest(request: Request, data: String): Request = request.newBuilder()
        .header(HttpHeaders.Authorization, "Bearer $data")
        .build()

    private fun buildBody(data: String) = RefreshUseCase.Params(
        PathEntity.REFRESH_TOKEN,
        RefreshTokenBodyRequestEntity(data)
    )

    private fun verifyPath(path: String): Boolean {
        if (path.contains(PathEntity.LOGOUT.value)) return true
        if (path.contains(PathEntity.GET_USER.value)) return true
        if (path.contains(PathEntity.REFRESH_TOKEN.value)) return true

        return false
    }

    private fun getToken(isToken: Boolean = true): String? {
        val response = runBlocking {
            if (isToken) storageUseCase.getData(StorageConstants.DATA_STORE_ACCESS_TOKEN)
                .first()
            else storageUseCase.getData(StorageConstants.DATA_STORE_REFRESH_TOKEN).first()
        }

        return response.ifEmpty { null }
    }

    private fun refresh(refreshToken: String): AuthResponseEntity? {
        val response = runBlocking {
            refreshUseCase(buildBody(refreshToken)).first()
        }

        return if (response.isRight()) (response as Either.Right).value.data else null
    }
}
