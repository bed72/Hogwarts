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

import com.bed.seller.presentation.commons.states.States

import com.bed.core.usecases.authentication.RecoverUseCase

import com.bed.test.rule.MainCoroutineRule
import com.bed.test.factories.authentication.AuthenticationFactory

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class RecoverViewModelTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @Mock
    private lateinit var useCase: RecoverUseCase

    private lateinit var viewModel: RecoverViewModel

    private lateinit var factory: AuthenticationFactory

    private lateinit var states: MutableList<States<Boolean>>

    @Before
    fun setUp() {
        states = mutableListOf()
        factory = AuthenticationFactory()
        viewModel = RecoverViewModel(useCase, rule.dispatcher)
    }

    @Test
    fun `Should issue first loading state when trying to recover account with successful return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(true))

        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(states) }

        viewModel.recover(factory.recoverValidParameter)

        assertEquals(states[AuthenticationFactory.INITIAL], States.Initial)
        assertEquals(states[AuthenticationFactory.LOADING], States.Loading)
        states[AuthenticationFactory.SUCCESS].let { assertTrue(it is States.Success && it.data) }

        job.cancel()
    }

    @Test
    fun `Should issue first loading state when trying to recover account with failure return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(false)).also { delay(1_000L) }

        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(states) }

        viewModel.recover(factory.recoverValidParameter)

        assertEquals(states[AuthenticationFactory.INITIAL], States.Initial)
        assertEquals(states[AuthenticationFactory.LOADING], States.Loading)
        assertFalse(states[AuthenticationFactory.FAILURE] is States.Failure)

        job.cancel()
    }

    @Test
    fun `Should recover account with successful return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(true))

        viewModel.recover(factory.recoverValidParameter)

        viewModel.state.value.let {
            it as States.Success
            assertTrue(it.data)
        }
    }

    @Test
    fun `Should recover account with failure return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(false))

        viewModel.recover(factory.recoverValidParameter)

        viewModel.state.value.let {
            it as States.Success
            assertFalse(it.data)
        }
    }
}
