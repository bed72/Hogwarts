package com.bed.seller.infrastructure.network.models.auth.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpBodyRequestModel(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("data")
    val data: SignUpNameBodyRequestModel?,
)

