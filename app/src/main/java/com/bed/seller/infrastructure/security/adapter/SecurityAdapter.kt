package com.bed.seller.infrastructure.security.adapter

import java.security.KeyStore
import java.security.SecureRandom

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_CBC
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7

import com.bed.seller.data.client.security.SecurityClient
import com.bed.seller.infrastructure.storage.StorageConstants

class SecurityAdapter : SecurityClient {
    private val iv = ByteArray(16)
    private val charset by lazy { charset(UTF_8) }
    private val cipher by lazy { Cipher.getInstance(TRANSFORMATION) }
    private val keyGenerator by lazy { KeyGenerator.getInstance(ALGORITHM) }

    private val keyStore by lazy { KeyStore.getInstance(PROVIDER).apply { load(null) } }

    init { SecureRandom().nextBytes(iv) }

    override fun encrypt(data: String): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, createKey())
//        cipher.update(data.toByteArray(charset))

        return cipher.doFinal(data.toByteArray(charset))
    }

    override fun decrypt(data: ByteArray): String {
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
//        cipher.update(data)

        return cipher.doFinal(data).toString(charset)
    }

    private fun getKey(): SecretKey = keyStore.getKey(KEY, null) as SecretKey

    private fun createKey(): SecretKey =
        keyGenerator.apply {
            init(
                KeyGenParameterSpec
                    .Builder(KEY, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                        .setKeySize(256)
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(PADDING)
                        .setUserAuthenticationRequired(false)
                        .setRandomizedEncryptionRequired(true)
                        .build()
            )
        }.generateKey()

    companion object {
        private const val UTF_8 = "UTF-8"
        private const val PROVIDER = "AndroidKeyStore"
        private const val BLOCK_MODE = BLOCK_MODE_CBC
        private const val ALGORITHM = KEY_ALGORITHM_AES
        private const val PADDING = ENCRYPTION_PADDING_PKCS7
        private const val KEY = StorageConstants.DATA_STORE_NAME
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}
