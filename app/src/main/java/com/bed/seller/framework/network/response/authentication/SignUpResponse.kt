package com.bed.seller.framework.network.response.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.bed.core.domain.models.authentication.SignUpModel

@Serializable
data class SignUpResponse(
    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("phone")
    val phone: String,
)

fun SignUpResponse.toModel() = SignUpModel(
    name = name,
    email = email,
    phone = phone,
)
