package com.bed.core.repositories

import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun get(value: String): Flow<String>

    suspend fun save(value: Pair<String, String>)
}
