package com.bed.core.domain.models.authentication

data class AuthenticationModel(
    val expireIn: Int,
    val accessToken: String,
    val refreshToken: String,
    val user: AuthenticationUserModel,
)

data class AuthenticationUserModel(
    val email: String,
    val userMetadata: AuthenticationMetadataModel,
)

data class AuthenticationMetadataModel(
    val name: String,
)
