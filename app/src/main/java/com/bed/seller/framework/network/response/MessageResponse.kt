package com.bed.seller.framework.network.response

import com.bed.core.entities.output.MessageOutput

data class MessageResponse(
    val message: String?
)

fun MessageResponse.toEntity() = MessageOutput(
    message ?: "Ops credenciais inválidas.",
)
