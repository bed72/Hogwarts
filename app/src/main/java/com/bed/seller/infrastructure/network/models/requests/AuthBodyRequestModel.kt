package com.bed.seller.infrastructure.network.models.requests

abstract class AuthBodyRequestModel {
    open val email: String? = null
    open val password: String? = null
    open val refreshToken: String? = null
}
