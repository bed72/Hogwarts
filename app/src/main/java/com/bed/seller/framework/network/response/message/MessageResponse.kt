package com.bed.seller.framework.network.response.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.core.domain.models.failure.MessageModel

@Serializable
data class MessageResponse(
    @SerialName("error")
    val error: String?,

    @SerialName("msg")
    val message: String?,

    @SerialName("error_description")
    val description: String?,
)

fun MessageResponse.toModel() = MessageModel(
    error ?: "Ops, uma falha occoreu.",
    message ?: "Não se preocupe estamos organizando tudo para você.",
    description ?: "Uma falha nos serviços internos pode ter ocorrido."
)
