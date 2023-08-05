package com.bed.core.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class UseCase<in P, out R> {
    operator fun invoke(parameters: P): Flow<R> = flow {
        emit(doWork(parameters))
    }

    protected abstract suspend fun doWork(parameters: P): R
}

abstract class FlowUseCase<in P, out R : Any> {

    suspend operator fun invoke(parameters: P): Flow<R> = createFlowObservable(parameters)

    protected abstract suspend fun createFlowObservable(parameters: P): Flow<R>
}
