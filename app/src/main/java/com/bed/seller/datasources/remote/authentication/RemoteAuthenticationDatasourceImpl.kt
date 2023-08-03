package com.bed.seller.datasources.remote.authentication

import javax.inject.Inject

import io.ktor.http.HttpMethod
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.request.url

import io.ktor.client.request.setBody

import com.bed.seller.framework.network.paths.ApiPath
import com.bed.seller.framework.network.adapters.request
import com.bed.seller.framework.network.adapters.HttpAdapter

import com.bed.core.domain.alias.SignUpType
import com.bed.core.domain.parameters.authentication.SignUpParameters
import com.bed.core.data.datasources.authentication.AuthenticationDatasource

import com.bed.seller.framework.network.response.message.toModel
import com.bed.seller.framework.network.response.authentication.toModel
import com.bed.seller.framework.network.response.message.MessageResponse
import com.bed.seller.framework.network.response.authentication.SignUpResponse

class RemoteAuthenticationDatasourceImpl @Inject constructor(
    private val client: HttpAdapter
) : AuthenticationDatasource {

    override suspend fun signUp(parameters: SignUpParameters): SignUpType {
        return client.ktor.request<MessageResponse, SignUpResponse> {
            setBody("mapper(params)")
            method = HttpMethod.Post
            url(ApiPath.SIGN_UP.value)
            contentType(ContentType.Application.Json)
        }
            .map { failure -> failure.toModel() }
            .mapLeft { success -> success.toModel() }
    }
}
