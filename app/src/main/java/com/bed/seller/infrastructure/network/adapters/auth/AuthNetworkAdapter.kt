package com.bed.seller.infrastructure.network.adapters.auth

import io.ktor.http.HttpMethod
import io.ktor.http.ContentType
import io.ktor.http.contentType

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.client.request.setBody

import com.bed.seller.BuildConfig

import com.bed.seller.data.client.AuthClient

import com.bed.seller.domain.alias.AuthEitherModelType

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity
import com.bed.seller.domain.entities.auth.tokens.RefreshTokenEntity
import com.bed.seller.domain.entities.auth.signin.SignInBodyRequestEntity
import com.bed.seller.domain.entities.auth.signup.SignUpBodyRequestEntity

import com.bed.seller.infrastructure.network.adapters.safe
import com.bed.seller.infrastructure.network.models.auth.AuthBodyRequestModel
import com.bed.seller.infrastructure.network.models.auth.tokens.RefreshTokenModel
import com.bed.seller.infrastructure.network.models.auth.signin.SignInBodyRequestModel
import com.bed.seller.infrastructure.network.models.auth.signup.SignUpBodyRequestModel
import com.bed.seller.infrastructure.network.models.auth.signup.SignUpNameBodyRequestModel

class AuthNetworkAdapter(private val httpClient: HttpClient) : AuthClient {
    override suspend fun invoke(path: PathEntity, params: AuthBodyRequestEntity): AuthEitherModelType =
        httpClient.safe {
            method = HttpMethod.Post
            setBody(buildBody(params))
            contentType(ContentType.Application.Json)
            url("${BuildConfig.BASE_URL}${path.value}")
        }

    private fun buildBody(params: AuthBodyRequestEntity): AuthBodyRequestModel? =
        when (params) {
            is RefreshTokenEntity -> RefreshTokenModel(params.refreshToken)
            is SignInBodyRequestEntity -> SignInBodyRequestModel(params.email, params.password)
            is SignUpBodyRequestEntity -> SignUpBodyRequestModel(
                params.email,
                params.password,
                SignUpNameBodyRequestModel(params.name)
            )
            else -> null
        }
}
