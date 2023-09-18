package com.bed.seller.presentation.ui.splash

import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

import org.mockito.Mock
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.test.runTest

import com.bed.core.usecases.authentication.IsLoggedInUseCase

@RunWith(MockitoJUnitRunner::class)
internal class SplashViewModelTest {
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
            whenever(useCase()).thenReturn(true)

            viewModel.isLoggedIn()

            val (success) = viewModel.states.value as SplashViewModel.States.IsLoggedIn
            assertEquals(true, success)
        }

    @Test
    fun `Should return false in IsLoggedIn State when trying is logged in with return failure`() =
        runTest {
            whenever(useCase()).thenReturn(false)

            viewModel.isLoggedIn()

            val (success) = viewModel.states.value as SplashViewModel.States.IsLoggedIn
            assertEquals(false, success)
        }
}
