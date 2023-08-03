package com.bed.seller.framework.network.request.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestModel(
    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,
)

