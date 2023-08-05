package com.bed.seller.framework.network.response.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.core.domain.models.authentication.AuthenticationModel
import com.bed.core.domain.models.authentication.AuthenticationUserModel
import com.bed.core.domain.models.authentication.AuthenticationMetadataModel

@Serializable
data class AuthenticationResponse(
    @SerialName("expires_in")
    val expireIn: Int,

    @SerialName("access_token")
    val accessToken: String,

    @SerialName("refresh_token")
    val refreshToken: String,

    @SerialName("user")
    val user: AuthenticationUserResponse,
)

fun AuthenticationResponse.toModel() = AuthenticationModel(
    expireIn = expireIn,
    accessToken = accessToken,
    refreshToken = refreshToken,
    user = user.toModel()
)

@Serializable
data class AuthenticationUserResponse(
    @SerialName("email")
    val email: String,

    @SerialName("user_metadata")
    val userMetadata: AuthenticationMetadataResponse,
)

fun AuthenticationUserResponse.toModel() = AuthenticationUserModel(
    email = email,
    userMetadata = userMetadata.toModel()
)

@Serializable
data class AuthenticationMetadataResponse(
    @SerialName("name")
    val name: String,
)

fun AuthenticationMetadataResponse.toModel() = AuthenticationMetadataModel(name = name)
