package com.bed.seller.data.client

import kotlinx.coroutines.flow.Flow

interface StorageClient {
    suspend fun get(key: String): Flow<String>
    suspend fun save(key: String, value: String)
}
