package com.bed.seller.infrastructure.network.models.requests.signup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpNameBodyRequestModel(
    @SerialName("name")
    val name: String,
)
