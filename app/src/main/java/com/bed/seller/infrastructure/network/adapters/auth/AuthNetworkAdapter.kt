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
import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity
import com.bed.seller.domain.entities.auth.signin.SignInBodyRequestEntity

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.signup.SignUpBodyRequestEntity

import com.bed.seller.infrastructure.network.adapters.safe
import com.bed.seller.infrastructure.network.models.requests.AuthBodyRequestModel
import com.bed.seller.infrastructure.network.models.requests.signin.SignInBodyRequestModel
import com.bed.seller.infrastructure.network.models.requests.signup.SignUpBodyRequestModel
import com.bed.seller.infrastructure.network.models.requests.signup.SignUpNameBodyRequestModel

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
            is SignInBodyRequestEntity -> SignInBodyRequestModel(params.email, params.password)
            is SignUpBodyRequestEntity -> SignUpBodyRequestModel(
                params.email,
                params.password,
                SignUpNameBodyRequestModel(params.name)
            )
            else -> null
        }
//        if (params is SignInBodyRequestEntity) SignInBodyRequestModel(params.email, params.password)
//        else SignUpBodyRequestModel(
//            params.email,
//            params.password,
//            SignUpNameBodyRequestModel(params.name)
//        )
}
