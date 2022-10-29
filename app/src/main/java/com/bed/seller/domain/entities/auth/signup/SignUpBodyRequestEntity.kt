package com.bed.seller.domain.entities.auth.signup

data class SignUpBodyRequestEntity(
    val name: String = "",
    val email: String = "",
    val password: String = "",
)

fun SignUpBodyRequestEntity.isNotEmpty(): Boolean =
    name.isNotEmpty() and email.isNotEmpty() and password.isNotEmpty()
