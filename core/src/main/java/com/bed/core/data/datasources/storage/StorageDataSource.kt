package com.bed.core.data.datasources.storage

import kotlinx.coroutines.flow.Flow

interface StorageDataSource {
    suspend fun get(value: String): Flow<String>

    suspend fun save(value: Pair<String, String>)
}
