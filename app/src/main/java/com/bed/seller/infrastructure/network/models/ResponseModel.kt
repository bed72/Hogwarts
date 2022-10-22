package com.bed.seller.infrastructure.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel<out T>(
    @SerialName("code")
    val status: Int,

    @SerialName("data")
    val data: T,
)
