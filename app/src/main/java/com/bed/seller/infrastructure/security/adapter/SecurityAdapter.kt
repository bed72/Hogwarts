package com.bed.seller.infrastructure.security.adapter

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import com.bed.seller.data.client.security.SecurityClient
import com.bed.seller.infrastructure.storage.StorageConstants
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class SecurityAdapter : SecurityClient {
    private val provider = "AndroidKeyStore"

    private val charset by lazy { charset("UTF-8") }
    private val cipher by lazy { Cipher.getInstance("AES/GCM/NoPadding") }
    private val keyStore by lazy { KeyStore.getInstance(provider).apply { load(null) } }
    private val keyGenerator by lazy { KeyGenerator.getInstance(KEY_ALGORITHM_AES, provider) }

    init {
        encrypt(StorageConstants.DATA_STORE_NAME, StorageConstants.DATA_STORE_NAME)
    }

    override fun encrypt(key: String, data: String): ByteArray {
        cipher.init(
            Cipher.ENCRYPT_MODE,
            generateSecretKey(key)
        )

        return cipher.doFinal(data.toByteArray(charset))
    }

    override fun decrypt(key: String, data: ByteArray): String {
        cipher.init(
            Cipher.DECRYPT_MODE,
            getSecretKey(key),
            GCMParameterSpec(128, cipher.iv)
        )

        return cipher.doFinal(data).toString(charset)
    }

    private fun getSecretKey(key: String): SecretKey =
        (keyStore.getEntry(key,null) as KeyStore.SecretKeyEntry).secretKey

    private fun generateSecretKey(key: String): SecretKey =
        keyGenerator.apply {
            init(
                KeyGenParameterSpec
                    .Builder(key, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                    .setBlockModes(BLOCK_MODE_GCM)
                    .setKeySize(128)
                    .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
}
