package com.bed.seller.infrastructure.network.models.responses.failure

import com.bed.seller.domain.entities.failure.MessageFailureResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageFailureResponseModel(
    @SerialName("error")
    val error: String? = null,

    @SerialName("msg")
    val message: String? = null,
)

fun MessageFailureResponseModel.toEntity() =
    MessageFailureResponseEntity(
        error = this.error ?: "",
        message = this.message ?: ""
    )
