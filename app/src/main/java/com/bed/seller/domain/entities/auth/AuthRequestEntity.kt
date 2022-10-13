package com.bed.seller.domain.entities.auth

data class AuthRequestEntity(
    val name: String? = null,
    val email: String = "",
    val password: String = "",
)

fun AuthRequestEntity.isNotEmpty(): Boolean =
    (name?.isNotEmpty() ?: true) and email.isNotEmpty() and password.isNotEmpty()
