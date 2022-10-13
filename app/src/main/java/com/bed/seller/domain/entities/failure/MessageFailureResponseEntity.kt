package com.bed.seller.domain.entities.failure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageFailureResponseEntity(
    @SerialName("msg")
    val message: String,
)
