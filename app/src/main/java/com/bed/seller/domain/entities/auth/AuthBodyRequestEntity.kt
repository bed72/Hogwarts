package com.bed.seller.domain.entities.auth

abstract class AuthBodyRequestEntity {
    abstract val email: String
    abstract val password: String
}
