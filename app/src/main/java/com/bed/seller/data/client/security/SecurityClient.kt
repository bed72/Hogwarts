package com.bed.seller.data.client.security

interface SecurityClient {
    fun encrypt(key: String, data: String): ByteArray
    fun decrypt(key: String, data: ByteArray): String
}
