package com.bed.seller.infrastructure.network.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthBodyRequestModel(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("data")
    val data: SignUpDataBodyRequestModel?,
)

