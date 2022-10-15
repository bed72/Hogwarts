package com.bed.seller.domain.entities.auth.signin

import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity

data class SignInBodyRequestEntity(
    override val email: String = "",
    override val password: String = "",
) : AuthBodyRequestEntity()

fun SignInBodyRequestEntity.isNotEmpty(): Boolean =
    email.isNotEmpty() and password.isNotEmpty()
