package com.bed.seller.data.client.storage

import kotlinx.coroutines.flow.Flow

interface StorageClient {
    suspend fun clearData()

    fun getData(params: String): Flow<String>
    suspend fun saveData(params: Pair<String, String>)

    fun getSecureData(params: String): Flow<String>
    suspend fun saveSecureData(params: Pair<String, String>)
}
