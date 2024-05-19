package com.bed.core.entities.output

data class AuthenticationOutput(
    val uid: String,
    val name: String,
    val email: String,
    val photo: String,
    val emailVerified: Boolean
)

