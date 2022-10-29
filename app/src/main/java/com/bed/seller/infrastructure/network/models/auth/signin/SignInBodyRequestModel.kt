package com.bed.seller.infrastructure.network.models.auth.signin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInBodyRequestModel(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,
)

