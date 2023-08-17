package com.bed.core.usecases.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCase

import com.bed.core.data.repositories.StorageRepository

interface SaveStorageUseCase {
    operator fun invoke(params: Pair<String, String>): Flow<Unit>
}

class SaveStorageUseCaseImpl @Inject constructor(
    private val useCase: CoroutinesUseCase,
    private val repository: StorageRepository
) : SaveStorageUseCase, UseCase<Pair<String, String>, Unit>() {
    override suspend fun doWork(parameters: Pair<String, String>) {
        withContext(useCase.io()) { repository.save(parameters) }
    }
}
