package com.bed.seller.domain.entities.auth.tokens

data class RefreshTokenBodyRequestEntity(
    val refreshToken: String = ""
)

fun RefreshTokenBodyRequestEntity.isNotEmpty(): Boolean = refreshToken.isNotEmpty()
