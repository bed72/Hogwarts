package com.bed.seller.domain.entities.auth.tokens

import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity

data class RefreshTokenEntity(
    override val refreshToken: String = ""
) : AuthBodyRequestEntity()
