package com.bed.seller.domain.entities.auth

data class AuthResponseEntity(
    val accessToken: String,
    val refreshToken: String,
)
