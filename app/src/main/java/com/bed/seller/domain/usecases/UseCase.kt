package com.bed.seller.domain.usecases

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow

abstract class UseCase<in P, R> {
    operator fun invoke(params: P): Flow<R> = flow {
        emit(doWork(params))
    }

    protected abstract suspend fun doWork(params: P): R
}
