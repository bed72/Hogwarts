package com.bed.core.usecases.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.FlowUseCase

import com.bed.core.data.repositories.StorageRepository
import com.bed.core.data.repositories.CoroutinesRepository

interface GetStorageUseCase {
    suspend operator fun invoke(parameter: String): Flow<String>
}

class GetStorageUseCaseImpl @Inject constructor(
    private val repositoryStorage: StorageRepository,
    private val coroutinesRepository: CoroutinesRepository,
) : GetStorageUseCase, FlowUseCase<String, String>() {

    override suspend fun createFlowObservable(parameter: String): Flow<String> =
        withContext(coroutinesRepository.io()) { repositoryStorage.get(parameter) }
}
