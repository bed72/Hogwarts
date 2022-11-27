package com.bed.seller.infrastructure.security.adapter

import java.security.KeyStore
import java.security.SecureRandom

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE

import com.bed.seller.data.client.security.SecurityClient
import com.bed.seller.infrastructure.storage.StorageConstants

class SecurityAdapter : SecurityClient {
    private val iv = ByteArray(16)

    private val charset by lazy { charset(UTF_8) }
    private val cipher by lazy { Cipher.getInstance(TRANSFORMATION) }
    private val keyStore by lazy { KeyStore.getInstance(PROVIDER).apply { load(null) } }
    private val keyGenerator by lazy { KeyGenerator.getInstance(KEY_ALGORITHM_AES, PROVIDER) }

    init { SecureRandom().nextBytes(iv) }

    override fun encrypt(data: String): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, createKey())

        return cipher.doFinal(data.toByteArray(charset))
    }

    override fun decrypt(data: ByteArray): String {
        cipher.init(Cipher.DECRYPT_MODE, getKey(), GCMParameterSpec(128, iv))

        return cipher.doFinal(data).toString(charset)
    }

    private fun getKey(): SecretKey = keyStore.getKey(KEY, null) as SecretKey

    private fun createKey(): SecretKey =
        keyGenerator.apply {
            init(
                KeyGenParameterSpec
                    .Builder(KEY, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE_GCM)
                        .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                        .setUserAuthenticationRequired(false)
                        .setRandomizedEncryptionRequired(true)
                        .build()
            )
        }.generateKey()

    companion object {
        private const val UTF_8 = "UTF-8"
        private const val PROVIDER = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val KEY = StorageConstants.DATA_STORE_NAME
    }
}
