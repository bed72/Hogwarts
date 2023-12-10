package com.bed.seller.presentation.ui.authentication.reset

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bed.core.usecases.authentication.ResetUseCase

import com.bed.seller.presentation.commons.states.States

import com.bed.test.rule.MainCoroutineRule
import com.bed.test.factories.authentication.AuthenticationFactory

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class ResetViewModelTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: ResetUseCase

    private  lateinit var viewModel: ResetViewModel

    private lateinit var factory: AuthenticationFactory

    private lateinit var states: MutableList<States<Boolean>>

    @Before
    fun setUp() {
        states = mutableListOf()
        factory = AuthenticationFactory()
        viewModel = ResetViewModel(useCase, rule.dispatcher)
    }

    @Test
    fun `Should issue first loading state when trying to reset password account with successful return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(true))

        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(states) }

        viewModel.reset(factory.resetValidParameter)

        assertEquals(states[AuthenticationFactory.INITIAL], States.Initial)
        assertEquals(states[AuthenticationFactory.LOADING], States.Loading)
        states[AuthenticationFactory.SUCCESS].let { assertTrue(it is States.Success && it.data) }

        job.cancel()
    }

    @Test
    fun `Should issue first loading state when trying to reset password account with failure return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(false))

        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(states) }

        viewModel.reset(factory.resetValidParameter)

        assertEquals(states[AuthenticationFactory.INITIAL], States.Initial)
        assertEquals(states[AuthenticationFactory.LOADING], States.Loading)
        assertFalse(states[AuthenticationFactory.FAILURE] is States.Failure)

        job.cancel()
    }

    @Test
    fun `Should reset password account with successful return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(true))

        viewModel.reset(factory.resetValidParameter)

        viewModel.state.value.let {
            it as States.Success
            assertTrue(it.data)
        }
    }

    @Test
    fun `Should reset password account with failure return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(false))

        viewModel.reset(factory.resetValidParameter)

        viewModel.state.value.let {
            it as States.Success
            assertFalse(it.data)
        }
    }
}
