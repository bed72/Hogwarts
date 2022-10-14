package com.bed.seller.infrastructure.network.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDataBodyRequestModel(
    @SerialName("name")
    val name: String,
)
