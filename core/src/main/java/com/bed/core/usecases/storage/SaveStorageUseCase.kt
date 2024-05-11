package com.bed.core.usecases.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import com.bed.core.usecases.UseCase

import com.bed.core.data.repositories.StorageRepository
import com.bed.core.data.repositories.CoroutinesRepository

interface SaveStorageUseCase {
    operator fun invoke(parameter: Pair<String, String>): Flow<Unit>
}

class SaveStorageUseCaseImpl @Inject constructor(
    private val repositoryStorage: StorageRepository,
    private val coroutinesRepository: CoroutinesRepository,
) : SaveStorageUseCase, UseCase<Pair<String, String>, Unit>() {
    override suspend fun doWork(parameter: Pair<String, String>) {
        withContext(coroutinesRepository.io()) { repositoryStorage.save(parameter) }
    }
}
