package com.bed.seller.infrastructure.network.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.seller.domain.entities.auth.AuthResponseEntity

@Serializable
data class AuthResponseModel(
    @SerialName("expires_in")
    val expiresIn: Int,

    @SerialName("access_token")
    val accessToken: String,

    @SerialName("refresh_token")
    val refreshToken: String,
)

fun AuthResponseModel.toEntity() =
    AuthResponseEntity(
        expiresIn = this.expiresIn,
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
    )
