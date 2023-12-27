package com.bed.test.rules

import org.junit.rules.TestWatcher
import org.junit.runner.Description

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher

import com.bed.core.usecases.coroutines.CoroutinesUseCase

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler()),
) : TestWatcher() {

    val dispatcher = object : CoroutinesUseCase {
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
