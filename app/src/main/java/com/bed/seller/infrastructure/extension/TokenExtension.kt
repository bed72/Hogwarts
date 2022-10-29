package com.bed.seller.infrastructure.extension

import java.lang.Exception

import com.auth0.android.jwt.JWT

import com.bed.seller.infrastructure.network.models.auth.AuthResponseModel

fun String.isExpired() = toJwt()?.isExpired(0) ?: true

private fun AuthResponseModel.getAccessTokenJWT(): JWT? = accessToken.toJwt()

fun AuthResponseModel.getUserIdFromAccessToken(): String =
    getAccessTokenJWT()?.getClaim("id")?.asString() ?: ""

private fun String.toJwt() = try {
    JWT(this)
} catch (exception: Exception) {
    null
}
