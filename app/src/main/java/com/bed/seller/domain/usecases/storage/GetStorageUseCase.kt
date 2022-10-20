package com.bed.seller.domain.usecases.storage

import kotlinx.coroutines.flow.Flow

interface GetStorageUseCase {
    suspend operator fun invoke(params: String): Flow<String>
}

interface SaveStorageUseCase {
    operator fun invoke(params: Params): Flow<Unit>

    data class Params(val data: Pair<String, String>)
}
