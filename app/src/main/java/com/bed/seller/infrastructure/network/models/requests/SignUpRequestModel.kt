package com.bed.seller.infrastructure.network.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestModel(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("data")
    val data: SignUpDataRequestModel?,
)

@Serializable
data class SignUpDataRequestModel(
    @SerialName("name")
    val name: String,
)
