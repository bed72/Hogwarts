package com.bed.seller.data.client.storage

import kotlinx.coroutines.flow.Flow

interface StorageClient {
    suspend fun clearDataStore()
    suspend fun get(params: String): Flow<String>
    suspend fun save(params: Pair<String, String>): Flow<String>
    suspend fun getSecure(params: String): Flow<String>
    suspend fun saveSecure(params: Pair<String, String>): Flow<String>
}
