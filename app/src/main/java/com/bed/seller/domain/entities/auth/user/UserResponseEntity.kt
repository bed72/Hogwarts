package com.bed.seller.domain.entities.auth.user

data class UserResponseEntity(
    val id: String,
    val email: String,
    val userMetadata: UserMetaDataResponseEntity
)

data class UserMetaDataResponseEntity(
    val name: String
)
