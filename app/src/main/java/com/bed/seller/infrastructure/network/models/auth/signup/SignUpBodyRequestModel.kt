package com.bed.seller.infrastructure.network.models.auth.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.seller.infrastructure.network.models.auth.AuthBodyRequestModel

@Serializable
data class SignUpBodyRequestModel(
    @SerialName("email")
    override val email: String,

    @SerialName("password")
    override val password: String,

    @SerialName("data")
    val data: SignUpNameBodyRequestModel?,
) : AuthBodyRequestModel()

