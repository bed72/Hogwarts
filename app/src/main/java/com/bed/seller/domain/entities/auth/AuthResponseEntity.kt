package com.bed.seller.domain.entities.auth

data class AuthResponseEntity(
    val expiresIn: Int,
    val accessToken: String,
    val refreshToken: String,
)
