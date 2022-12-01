package com.bed.seller.domain.usecases.storage

import kotlinx.coroutines.flow.Flow

interface StorageUseCase {
    suspend fun clearData()

    suspend fun getData(params: String): Flow<String>
    suspend fun saveData(params: Pair<String, String>)
}
