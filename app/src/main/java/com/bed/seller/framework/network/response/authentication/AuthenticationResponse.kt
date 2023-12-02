package com.bed.seller.framework.network.response.authentication

import com.bed.core.domain.models.authentication.AuthenticationModel

data class AuthenticationResponse(
    val uid: String,
    val name: String?,
    val email: String?,
    val photo: String?,
    val emailVerified: Boolean
)

fun AuthenticationResponse.toModel() = AuthenticationModel(
    uid = uid,
    name = name ?: "",
    email = email ?: "",
    photo = photo ?: "",
    emailVerified = emailVerified
)
