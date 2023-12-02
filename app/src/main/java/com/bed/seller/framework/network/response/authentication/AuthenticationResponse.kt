package com.bed.seller.framework.network.response.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.core.domain.models.authentication.AuthenticationModel

@Serializable
data class AuthenticationResponse(
    @SerialName("uid")
    val uid: String,

    @SerialName("displayName")
    val name: String?,

    @SerialName("email")
    val email: String?,

    @SerialName("photoUrl")
    val photo: String?,

    @SerialName("emailVerified")
    val emailVerified: Boolean
)

fun AuthenticationResponse.toModel() = AuthenticationModel(
    uid = uid,
    name = name ?: "",
    email = email ?: "",
    photo = photo ?: "",
    emailVerified = emailVerified
)
