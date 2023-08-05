package com.bed.seller.datasources.remote.authentication

import javax.inject.Inject

import io.ktor.http.HttpMethod
import io.ktor.client.request.url
import io.ktor.client.request.setBody

import com.bed.core.domain.alias.SignUpType
import com.bed.core.domain.parameters.authentication.SignUpParameters

import com.bed.core.data.datasources.authentication.AuthenticationDatasource

import com.bed.seller.framework.network.paths.ApiPath
import com.bed.seller.framework.network.adapters.request
import com.bed.seller.framework.network.adapters.HttpAdapter
import com.bed.seller.framework.network.response.message.toModel
import com.bed.seller.framework.network.response.authentication.toModel
import com.bed.seller.framework.network.response.message.MessageResponse
import com.bed.seller.framework.network.response.authentication.AuthenticationResponse

class RemoteAuthenticationDatasourceImpl @Inject constructor(
    private val client: HttpAdapter
) : AuthenticationDatasource {

    override suspend fun signUp(parameters: SignUpParameters): SignUpType =
        client.ktor.request<MessageResponse, AuthenticationResponse> {
            setBody("mapper(params)")
            method = HttpMethod.Post
            url(ApiPath.SIGN_UP.value)
        }
            .map { success -> success.toModel() }
            .mapLeft { failure -> failure.toModel() }

}
