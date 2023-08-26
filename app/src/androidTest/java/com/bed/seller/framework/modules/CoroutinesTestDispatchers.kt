package com.bed.seller.framework.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher

import com.bed.core.usecases.coroutines.CoroutinesUseCase


@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesTestDispatchers(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
) : CoroutinesUseCase {
    override fun io(): CoroutineDispatcher = dispatcher
    override fun main(): CoroutineDispatcher = dispatcher
    override fun default(): CoroutineDispatcher = dispatcher
    override fun unconfined(): CoroutineDispatcher = dispatcher
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesTestModule {
    @Provides
    fun provideTestDispatchers(): CoroutinesUseCase = CoroutinesTestDispatchers()
}
