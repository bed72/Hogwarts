package com.bed.seller.data.datasources

import kotlinx.coroutines.flow.Flow

interface StorageDatasource {
    suspend fun get(value: String): Flow<String>

    suspend fun save(value: Pair<String, String>)
}
