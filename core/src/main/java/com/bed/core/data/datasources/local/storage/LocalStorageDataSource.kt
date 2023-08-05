package com.bed.core.data.datasources.local.storage

import kotlinx.coroutines.flow.Flow

interface LocalStorageDataSource {
    suspend fun get(value: String): Flow<String>

    suspend fun save(value: Pair<String, String>)
}
