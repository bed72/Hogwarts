package com.bed.seller.framework.network.response

import com.bed.core.entities.output.AuthenticationOutput

data class AuthenticationResponse(
    val uid: String,
    val name: String?,
    val email: String?,
    val photo: String?,
    val emailVerified: Boolean
)

fun AuthenticationResponse.toEntity() = AuthenticationOutput(
    uid = uid,
    name = name ?: "",
    email = email ?: "",
    photo = photo ?: "",
    emailVerified = emailVerified
)
