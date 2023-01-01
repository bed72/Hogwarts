package com.bed.seller.infrastructure.network.adapters.auth

import io.ktor.http.HttpMethod
import io.ktor.http.ContentType
import io.ktor.http.contentType

import com.bed.seller.BuildConfig

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.client.request.setBody

import com.bed.seller.data.client.auth.RefreshClient

import com.bed.seller.data.alias.AuthEitherModelType

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.tokens.RefreshTokenBodyRequestEntity

import com.bed.seller.infrastructure.network.adapters.safe
import com.bed.seller.infrastructure.network.models.auth.tokens.RefreshTokenModel

class RefreshAdapter(private val httpClient: HttpClient) : RefreshClient {
    override suspend fun invoke(path: PathEntity, params: RefreshTokenBodyRequestEntity): AuthEitherModelType =
        httpClient.safe {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(RefreshTokenModel(params.refreshToken))
            url("${BuildConfig.BASE_URL}${path.value}")
        }
}
