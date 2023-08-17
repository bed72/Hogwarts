package com.bed.core.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class UseCaseWithoutParameter<out R> {
    operator fun invoke(): Flow<R> = flow {
        emit(doWork())
    }

    protected abstract suspend fun doWork(): R
}

abstract class UseCase<in P, out R> {
    operator fun invoke(parameter: P): Flow<R> = flow {
        emit(doWork(parameter))
    }

    protected abstract suspend fun doWork(parameters: P): R
}

abstract class FlowUseCaseWithoutParameter<out R : Any> {
    suspend operator fun invoke(): Flow<R> = createFlowObservable()

    protected abstract suspend fun createFlowObservable(): Flow<R>
}

abstract class FlowUseCase<in P, out R : Any> {
    suspend operator fun invoke(parameter: P): Flow<R> = createFlowObservable(parameter)

    protected abstract suspend fun createFlowObservable(parameters: P): Flow<R>
}
