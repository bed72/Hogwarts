package com.bed.seller.domain.entities.paths

enum class PathEntity(val value: String) {
    SIGN_UP("/auth/v1/signup"),
    SIGN_IN("/auth/v1/token")
}
