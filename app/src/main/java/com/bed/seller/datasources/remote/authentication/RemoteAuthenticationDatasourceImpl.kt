package com.bed.seller.datasources.remote.authentication

import com.bed.core.data.datasources.authentication.AuthenticationDatasource
import com.bed.core.domain.alias.SignUpType
import com.bed.core.domain.parameters.authentication.SignUpParameters
import com.bed.seller.framework.network.adapters.HttpAdapter
import com.bed.seller.framework.network.adapters.request
import com.bed.seller.framework.network.paths.ApiPath
import com.bed.seller.framework.network.response.authentication.AuthenticationResponse
import com.bed.seller.framework.network.response.authentication.toModel
import com.bed.seller.framework.network.response.message.MessageResponse
import com.bed.seller.framework.network.response.message.toModel
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import javax.inject.Inject

class RemoteAuthenticationDatasourceImpl @Inject constructor(
    private val client: HttpAdapter
) : AuthenticationDatasource {

    override suspend fun signUp(parameters: SignUpParameters): SignUpType {
        return client.ktor.request<MessageResponse, AuthenticationResponse> {
            setBody("mapper(params)")
            method = HttpMethod.Post
            url(ApiPath.SIGN_UP.value)
        }
            .map { failure -> failure.toModel() }
            .mapLeft { success -> success.toModel() }
    }
}
