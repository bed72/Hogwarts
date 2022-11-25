package com.bed.seller.domain.usecases.storage

import kotlinx.coroutines.flow.Flow

interface StorageUseCase {
    suspend fun clearDataStore()

    suspend fun get(params: String): Flow<String>
    suspend fun save(params: Pair<String, String>): Flow<String>

    suspend fun getSecure(params: String): Flow<String>
    suspend fun saveSecure(params: Pair<String, String>): Flow<String>
}
