package com.bed.seller.domain.entities.auth.signin

data class SignInBodyRequestEntity(
    val email: String = "",
    val password: String = "",
)

fun SignInBodyRequestEntity.isNotEmpty(): Boolean =
    email.isNotEmpty() and password.isNotEmpty()
