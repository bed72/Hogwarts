package com.bed.seller.framework.network.response.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.core.domain.models.failure.MessageModel

@Serializable
data class MessageResponse(
    @SerialName("msg")
    val message: String?
)

fun MessageResponse.toModel() = MessageModel(
    message ?: "Ops credenciais inválidas.",
)
