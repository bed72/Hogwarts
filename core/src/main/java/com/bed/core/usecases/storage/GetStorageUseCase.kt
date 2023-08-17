package com.bed.core.usecases.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.FlowUseCase

import com.bed.core.usecases.coroutines.CoroutinesUseCase
import com.bed.core.data.repositories.StorageRepository

interface GetStorageUseCase {
    suspend operator fun invoke(parameters: String): Flow<String>
}

class GetStorageUseCaseImpl @Inject constructor(
    private val useCase: CoroutinesUseCase,
    private val repository: StorageRepository
) : GetStorageUseCase, FlowUseCase<String, String>() {

    override suspend fun createFlowObservable(parameters: String): Flow<String> =
        withContext(useCase.io()) { repository.get(parameters) }

}
