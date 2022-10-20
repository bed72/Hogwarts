package com.bed.seller.infrastructure.network.models.auth.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpNameBodyRequestModel(
    @SerialName("name")
    val name: String,
)
