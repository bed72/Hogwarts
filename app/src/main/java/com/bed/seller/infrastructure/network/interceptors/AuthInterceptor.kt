package com.bed.seller.infrastructure.network.interceptors

import okhttp3.Response
import okhttp3.Interceptor

import io.ktor.http.HttpStatusCode
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString

import com.bed.seller.infrastructure.extension.isExpired
import com.bed.seller.infrastructure.configuration.JSON_CONFIG
import com.bed.seller.infrastructure.network.models.auth.AuthResponseModel

import com.bed.seller.domain.usecases.auth.AuthRefreshUseCase

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.tokens.RefreshTokenBodyRequestEntity

class AuthInterceptor : Interceptor, KoinComponent {
    private val json = JSON_CONFIG
    private val refreshUseCase: AuthRefreshUseCase by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(chain.request())
        val url = request.url

        if (url.pathSegments.contains("auth")) {
            toModel<AuthResponseModel>(response)?.let { model ->
                if (model.accessToken.isExpired()) {
                    runBlocking {
                        refreshUseCase(buildBody(model)).collect { data ->
                            data.fold(
                                { response.close() },
                                { response.close() }
                            )
                        }
                    }
                }
            }
        }

        return response
    }

    private fun buildBody(model: AuthResponseModel) = AuthRefreshUseCase.Params(
        PathEntity.REFRESH_TOKEN,
        RefreshTokenBodyRequestEntity(model.refreshToken)
    )

    private inline fun <reified T> toModel(response: Response): T? =
        when (response.code) {
            HttpStatusCode.OK.value, HttpStatusCode.Created.value -> {
                val body = response.peekBody(Long.MAX_VALUE)

                json.decodeFromString<T>(body.string())
            }
            else -> null
    }
}
