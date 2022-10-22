package com.bed.seller.data.client

import com.bed.seller.domain.alias.ResponseStorageType

interface StorageClient {
    suspend fun get(params: String): ResponseStorageType
    suspend fun save(params: Pair<String, String>): ResponseStorageType
}
