package com.bed.seller.infrastructure.network.models.requests.signin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.seller.infrastructure.network.models.requests.AuthBodyRequestModel

@Serializable
data class SignInBodyRequestModel(
    @SerialName("email")
    override val email: String,

    @SerialName("password")
    override val password: String,
) : AuthBodyRequestModel()

