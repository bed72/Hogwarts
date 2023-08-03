package com.bed.core.domain.parameters.authentication

data class SignUpParameters(
    val name: String,
    val email: String,
    val password: String
)
