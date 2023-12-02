package com.bed.seller.presentation.ui.authentication.recover

import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.junit.runner.RunWith

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertEquals

import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bed.seller.presentation.commons.states.States

import com.bed.core.usecases.authentication.RecoverUseCase

import com.bed.test.rule.MainCoroutineRule
import com.bed.test.factories.authentication.AuthenticationFactory

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class RecoverViewModelTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: RecoverUseCase

    private lateinit var factory: AuthenticationFactory

    private lateinit var emissions: MutableList<States<Boolean>>

    private lateinit var viewModel: RecoverViewModel

    @Before
    fun setUp() {
        emissions = mutableListOf()
        factory = AuthenticationFactory()
        viewModel = RecoverViewModel(useCase, rule.dispatcher)
    }

    @Test
    fun `Should emit Loading State when trying to recover with return success`() = runTest {
        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(emissions) }

        whenever(useCase(any())).thenReturn(flowOf(true)).run { delay(1_000L) }

        viewModel.recover(factory.recoverValidParameter)

        assertEquals(emissions[0], States.Initial)
        assertEquals(emissions[1], States.Loading)
        emissions[2].let { assertTrue(it is States.Success && it.data) }

        job.cancel()
    }

    @Test
    fun `Should emit Loading State when trying to recover with return failure`() = runTest {
        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(emissions) }

        whenever(useCase(any())).thenReturn(flowOf(false)).run { delay(1_000L) }

        viewModel.recover(factory.recoverValidParameter)

        assertEquals(emissions[0], States.Initial)
        assertEquals(emissions[1], States.Loading)
        assertFalse(emissions[2] is States.Failure)

        job.cancel()
    }
}
