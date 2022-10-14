package com.bed.seller.domain.entities.auth

data class AuthBodyRequestEntity(
    val name: String? = null,
    val email: String = "",
    val password: String = "",
)

fun AuthBodyRequestEntity.isNotEmpty(): Boolean =
    (name?.isNotEmpty() ?: true) and email.isNotEmpty() and password.isNotEmpty()
