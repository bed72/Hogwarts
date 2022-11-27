package com.bed.seller.data.client.security

interface SecurityClient {
    fun encrypt(data: String): ByteArray
    fun decrypt(data: ByteArray): String
}
