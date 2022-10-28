package com.bed.seller.infrastructure.network.models.auth.tokens

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenModel(
    @SerialName("refresh_token")
    val refreshToken: String
)
