package com.bed.seller.infrastructure.network.models.auth.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.seller.domain.entities.auth.user.UserResponseEntity
import com.bed.seller.domain.entities.auth.user.UserMetaDataResponseEntity

@Serializable
data class UserResponseModel(
    @SerialName("id")
    val id: String,

    @SerialName("email")
    val email: String,

    @SerialName("user_metadata")
    val userMetadata: UserMetaDataResponseModel
)

fun UserResponseModel.toEntity() =
    UserResponseEntity(
        id = this.id,
        email = this.email,
        userMetadata = this.userMetadata.toEntity()
    )


@Serializable
data class UserMetaDataResponseModel(
    @SerialName("name")
    val name: String
)

fun UserMetaDataResponseModel.toEntity() =
    UserMetaDataResponseEntity(
        name = this.name
    )
