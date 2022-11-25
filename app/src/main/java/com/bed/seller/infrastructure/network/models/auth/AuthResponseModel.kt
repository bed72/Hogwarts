package com.bed.seller.infrastructure.network.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.seller.domain.entities.auth.AuthResponseEntity

import com.bed.seller.infrastructure.network.models.auth.user.toEntity
import com.bed.seller.infrastructure.network.models.auth.user.UserResponseModel

@Serializable
data class AuthResponseModel(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("refresh_token")
    val refreshToken: String,

    @SerialName("user")
    val user: UserResponseModel
)

fun AuthResponseModel.toEntity() =
    AuthResponseEntity(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        user = this.user.toEntity()
    )
