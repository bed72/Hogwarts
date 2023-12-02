package com.bed.core.domain.models.authentication

data class AuthenticationModel(
    val uid: String,
    val name: String,
    val email: String,
    val photo: String,
    val emailVerified: Boolean
)

