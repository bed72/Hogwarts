package com.bed.seller.infrastructure.network.adapters.auth

import io.ktor.http.HttpMethod
import io.ktor.http.ContentType
import io.ktor.http.contentType

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.client.request.setBody

import com.bed.seller.BuildConfig
import com.bed.seller.data.client.SignUpClient
import com.bed.seller.domain.alias.AuthEitherModelType
import com.bed.seller.domain.entities.auth.AuthRequestEntity

import com.bed.seller.infrastructure.network.adapters.safe
import com.bed.seller.infrastructure.network.models.requests.SignUpRequestModel
import com.bed.seller.infrastructure.network.models.requests.SignUpDataRequestModel

class AuthNetworkAdapter(private val httpClient: HttpClient) : SignUpClient {
    override suspend fun signUp(params: AuthRequestEntity): AuthEitherModelType =
        httpClient.safe {
            method = HttpMethod.Post
            setBody(buildBody(params))
            contentType(ContentType.Application.Json)
            url("${BuildConfig.BASE_URL}/auth/v1/signup")
        }

    private fun buildBody(params: AuthRequestEntity): SignUpRequestModel {
        val data = params.name?.let { SignUpDataRequestModel(it) }

        return SignUpRequestModel(params.email, params.password, data)
    }
}
