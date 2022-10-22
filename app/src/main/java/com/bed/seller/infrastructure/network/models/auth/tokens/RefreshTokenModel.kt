package com.bed.seller.infrastructure.network.models.auth.tokens

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.seller.infrastructure.network.models.auth.AuthBodyRequestModel

@Serializable
data class RefreshTokenModel(
    @SerialName("refresh_token")
    override val refreshToken: String
) : AuthBodyRequestModel()
