package com.bed.seller.presentation.ui.authentication.recover

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

    @Mock
    private lateinit var observer: Observer<RecoverViewModel.States>

    private lateinit var factory: AuthenticationFactory

    private lateinit var viewModel: RecoverViewModel

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
        viewModel = RecoverViewModel(rule.dispatcher, useCase)
            .apply { states.observeForever(observer) }
    }

    @Test
    fun `Should issue the Loading Status when verifying that you are recover with a successful return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(true))

        viewModel.recover(factory.recoverValidParameter)

        verify(observer).onChanged(isA<RecoverViewModel.States.Loading>())
        verify(observer).onChanged(isA<RecoverViewModel.States.Recover>())
    }

    @Test
    fun `Should issue the Loading Status when verifying that you are recover with a failure return`() = runTest {
        whenever(useCase(any())).thenReturn(flowOf(false))

        viewModel.recover(factory.recoverValidParameter)

        verify(observer).onChanged(isA<RecoverViewModel.States.Loading>())
        verify(observer).onChanged(isA<RecoverViewModel.States.Recover>())
    }

    @Test
    fun `Should return true in Success State when trying is recover with return success`() =
        runTest {
            whenever(useCase(any())).thenReturn(flowOf(true))

            viewModel.recover(factory.recoverValidParameter)

            val (success) = viewModel.states.value as RecoverViewModel.States.Recover
            assertEquals(true, success)
        }

    @Test
    fun `Should return true in Success State when trying is recover with return failure`() =
        runTest {
            whenever(useCase(any())).thenReturn(flowOf(false))

            viewModel.recover(factory.recoverValidParameter)

            val (success) = viewModel.states.value as RecoverViewModel.States.Recover
            assertEquals(false, success)
        }
}
