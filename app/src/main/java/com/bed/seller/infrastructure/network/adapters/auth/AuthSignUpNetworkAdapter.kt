package com.bed.seller.infrastructure.network.adapters.auth

import io.ktor.http.HttpMethod
import io.ktor.http.ContentType
import io.ktor.http.contentType

import com.bed.seller.BuildConfig

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.client.request.setBody

import com.bed.seller.data.client.AuthSignUpClient

import com.bed.seller.domain.alias.AuthEitherModelType

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.signup.SignUpBodyRequestEntity

import com.bed.seller.infrastructure.network.adapters.safe
import com.bed.seller.infrastructure.network.models.auth.signup.SignUpBodyRequestModel
import com.bed.seller.infrastructure.network.models.auth.signup.SignUpNameBodyRequestModel

class AuthSignUpNetworkAdapter(private val httpClient: HttpClient) : AuthSignUpClient {
    override suspend fun invoke(
        path: PathEntity,
        params: SignUpBodyRequestEntity
    ): AuthEitherModelType =
        httpClient.safe {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            url("${BuildConfig.BASE_URL}${path.value}")
            setBody(
                SignUpBodyRequestModel(
                    params.email,
                    params.password,
                    SignUpNameBodyRequestModel(params.name)
                )
            )
        }
}
