package com.bed.seller.framework.network.request.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String
)
