package com.bed.seller.domain.usecases.storage

import kotlinx.coroutines.flow.Flow

interface StorageUseCase {
    suspend fun get(key: String): Flow<String>
    suspend fun save(key: String, value: String)
}
