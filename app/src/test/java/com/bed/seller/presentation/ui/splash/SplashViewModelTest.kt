package com.bed.seller.presentation.ui.splash

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

import org.mockito.Mock
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bed.test.rule.MainCoroutineRule

import com.bed.core.usecases.authentication.IsLoggedInUseCase

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class SplashViewModelTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: IsLoggedInUseCase

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setUp() {
        viewModel = SplashViewModel(useCase)
    }

    @Test
    fun `Should return true in IsLoggedIn State when trying is logged in with return success`() =
        runTest {
            whenever(useCase()).thenReturn(flowOf(true))

            viewModel.isLoggedIn()

            val (success) = viewModel.state.value as SplashViewModel.States.IsLoggedIn
            assertEquals(true, success)
        }

    @Test
    fun `Should return false in IsLoggedIn State when trying is logged in with return failure`() =
        runTest {
            whenever(useCase()).thenReturn(flowOf(false))

            viewModel.isLoggedIn()

            val (success) = viewModel.state.value as SplashViewModel.States.IsLoggedIn
            assertEquals(false, success)
        }
}
