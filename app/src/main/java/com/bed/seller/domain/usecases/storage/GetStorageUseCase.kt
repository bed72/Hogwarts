package com.bed.seller.domain.usecases.storage


import com.bed.seller.domain.alias.ResponseStorageType

interface GetStorageUseCase {
    suspend operator fun invoke(params: String): ResponseStorageType
}

interface SaveStorageUseCase {
    suspend operator fun invoke(params: Pair<String, String>): ResponseStorageType
}
