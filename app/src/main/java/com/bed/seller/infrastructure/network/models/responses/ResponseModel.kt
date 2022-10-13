package com.bed.seller.infrastructure.network.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.seller.domain.entities.ResponseEntity

@Serializable
data class ResponseModel<out T>(
    @SerialName("code")
    val status: Int,

    @SerialName("data")
    val data: T,
)
