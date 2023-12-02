package com.bed.seller.presentation.ui.splash

import org.junit.Test
import org.junit.runner.RunWith

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse

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

    @Test
    fun `Should get data with failure`() = runTest {
        whenever(useCase()).thenReturn(false)

        viewModel = SplashViewModel(useCase)

        viewModel.state.value.let {
            it as SplashViewModel.States.IsLoggedIn
            assertFalse(it.isSuccess)
        }
    }

    @Test
    fun `Should get data with success`() = runTest {
        whenever(useCase()).thenReturn(true)

        viewModel = SplashViewModel(useCase)

        viewModel.state.value.let {
            it as SplashViewModel.States.IsLoggedIn
            assertTrue(it.isSuccess)
        }
    }
}
