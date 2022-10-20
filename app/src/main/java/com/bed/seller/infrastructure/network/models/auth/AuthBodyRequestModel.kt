package com.bed.seller.infrastructure.network.models.auth

import kotlinx.serialization.SerialName

abstract class AuthBodyRequestModel {
    @SerialName("email")
    open val email: String? = null

    @SerialName("password")
    open val password: String? = null

    @SerialName("refresh_token")
    open val refreshToken: String? = null
}
