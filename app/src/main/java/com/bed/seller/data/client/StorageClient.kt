package com.bed.seller.data.client

import kotlinx.coroutines.flow.Flow

interface StorageClient {
    fun get(key: String): Flow<String>
    suspend fun save(data: Pair<String, String>)
}
