package com.bed.seller.framework.network.request.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("data")
    val data: SignUpDataRequest,
)

@Serializable
data class SignUpDataRequest(
    @SerialName("name")
    val name: String,
)
