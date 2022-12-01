package com.bed.seller.domain.entities.auth

import com.bed.seller.domain.entities.auth.user.UserResponseEntity

data class AuthResponseEntity(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponseEntity
)
