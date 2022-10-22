package com.bed.seller.domain.entities.auth.signup

import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity

data class SignUpBodyRequestEntity(
    val name: String = "",
    override val email: String = "",
    override val password: String = "",
) : AuthBodyRequestEntity()

fun SignUpBodyRequestEntity.isNotEmpty(): Boolean =
    name.isNotEmpty() and email.isNotEmpty() and password.isNotEmpty()
