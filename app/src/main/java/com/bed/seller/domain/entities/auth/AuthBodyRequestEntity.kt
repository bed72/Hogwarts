package com.bed.seller.domain.entities.auth

abstract class AuthBodyRequestEntity {
    open val email: String? = null
    open val password: String? = null
    open val refreshToken: String? = null
}
