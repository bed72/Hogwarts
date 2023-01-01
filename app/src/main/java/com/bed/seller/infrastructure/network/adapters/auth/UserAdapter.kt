package com.bed.seller.infrastructure.network.adapters.auth

import io.ktor.http.HttpMethod
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.HttpClient
import io.ktor.client.request.url

import com.bed.seller.BuildConfig

import com.bed.seller.data.client.auth.UserClient

import com.bed.seller.data.alias.UserEitherModelType
import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.infrastructure.network.adapters.safe

class UserAdapter(private val httpClient: HttpClient) : UserClient {
    override suspend fun invoke(path: PathEntity): UserEitherModelType =
        httpClient.safe {
            method = HttpMethod.Get
            contentType(ContentType.Application.Json)
            url("${BuildConfig.BASE_URL}${path.value}")
        }
}
