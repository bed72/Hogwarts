package com.bed.seller.datasources.remote

import javax.inject.Inject

import io.ktor.http.HttpMethod
import io.ktor.client.request.url
import io.ktor.client.request.setBody

import com.bed.core.domain.alias.AuthenticationType
import com.bed.core.domain.parameters.authentication.SignUpParameter
import com.bed.core.domain.parameters.authentication.SignInParameter

import com.bed.core.data.datasources.remote.RemoteAuthenticationDatasource

import com.bed.seller.mappers.authentication.SignUpMapper
import com.bed.seller.mappers.authentication.SignInMapper

import com.bed.seller.framework.constants.PathConstant
import com.bed.seller.framework.network.clients.request
import com.bed.seller.framework.network.clients.HttpClient
import com.bed.seller.framework.network.response.message.toModel
import com.bed.seller.framework.network.response.authentication.toModel
import com.bed.seller.framework.network.response.message.MessageResponse
import com.bed.seller.framework.network.response.authentication.AuthenticationResponse

class RemoteAuthenticationDatasourceImpl @Inject constructor(
    private val client: HttpClient,
    private val signUpMapper: SignUpMapper,
    private val signInMapper: SignInMapper
) : RemoteAuthenticationDatasource {

    override suspend fun signUp(parameter: SignUpParameter): AuthenticationType =
        client.ktor.request<MessageResponse, AuthenticationResponse> {
            method = HttpMethod.Post
            url(PathConstant.SIGN_UP.value)
            setBody(signUpMapper(parameter))
        }
            .map { success -> success.toModel() }
            .mapLeft { failure -> failure.toModel() }

    override suspend fun signIn(parameter: SignInParameter): AuthenticationType =
        client.ktor.request<MessageResponse, AuthenticationResponse> {
            method = HttpMethod.Post
            url(PathConstant.SIGN_IN.value)
            setBody(signInMapper(parameter))
        }
            .map { success -> success.toModel() }
            .mapLeft { failure -> failure.toModel() }
}
