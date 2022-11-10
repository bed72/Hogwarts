package com.bed.seller.infrastructure.network.models.failure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.seller.domain.entities.failure.MessageFailureResponseEntity

@Serializable
data class MessageFailureResponseModel(
    @SerialName("msg")
    val message: String? = null,

    @SerialName("error_description")
    val errorDescription: String? = null,
)

fun MessageFailureResponseModel.toEntity() =
    MessageFailureResponseEntity(
        message = this.message ?: "",
        errorDescription = this.errorDescription ?: "",
    )
