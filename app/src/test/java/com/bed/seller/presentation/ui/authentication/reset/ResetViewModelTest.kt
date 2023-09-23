package com.bed.seller.presentation.ui.authentication.reset

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.isA
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.lifecycle.Observer
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bed.test.rule.MainCoroutineRule

import com.bed.core.usecases.authentication.ResetUseCase
import com.bed.test.factories.AuthenticationFactory

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class ResetViewModelTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: ResetUseCase

    @Mock
    private lateinit var observer: Observer<ResetViewModel.States>

    private lateinit var factory: AuthenticationFactory

    private lateinit var viewModel: ResetViewModel

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
        viewModel = ResetViewModel(useCase, rule.dispatcher)
            .apply { states.observeForever(observer) }
    }

    @Test
    fun `Should emit Loading State when trying to reset password with return success`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(true))

        viewModel.reset(factory.resetValidParameter)

        verify(observer).onChanged(isA<ResetViewModel.States.Loading>())
        verify(observer).onChanged(isA<ResetViewModel.States.Reset>())
    }

    @Test
    fun `Should emit Loading State when trying to reset password with return failure`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(false))

        viewModel.reset(factory.resetValidParameter)

        verify(observer).onChanged(isA<ResetViewModel.States.Loading>())
        verify(observer).onChanged(isA<ResetViewModel.States.Reset>())
    }

    @Test
    fun `Should return true in Reset State when trying is reset password with return success`() =
        runTest {
            whenever(useCase(any())).thenReturn(flowOf(true))

            viewModel.reset(factory.resetValidParameter)

            val (success) = viewModel.states.value as ResetViewModel.States.Reset
            assertEquals(true, success)
        }

    @Test
    fun `Should return false in Reset State when trying is reset password with return failure`() =
        runTest {
            whenever(useCase(any())).thenReturn(flowOf(false))

            viewModel.reset(factory.resetInvalidParameter)

            val (success) = viewModel.states.value as ResetViewModel.States.Reset
            assertEquals(false, success)
        }
}
