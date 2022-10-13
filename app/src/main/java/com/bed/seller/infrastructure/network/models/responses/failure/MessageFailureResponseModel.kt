package com.bed.seller.infrastructure.network.models.responses.failure

import com.bed.seller.domain.entities.failure.MessageFailureResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageFailureResponseModel(
    @SerialName("msg")
    val message: String,
)

fun MessageFailureResponseModel.toEntity() =
    MessageFailureResponseEntity(
        message = this.message
    )
