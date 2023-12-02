package com.bed.seller.presentation.ui.splash

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import junit.framework.TestCase.assertTrue

import org.mockito.Mock
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bed.test.rule.MainCoroutineRule

import com.bed.seller.presentation.commons.states.States

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

    @Test
    fun `Should get data with failure`() = runTest {
        whenever(useCase()).thenReturn(false)

        viewModel = SplashViewModel(useCase, rule.dispatcher)

        viewModel.state.value.let {
            assertTrue(it is States.Failure)
        }
    }

    @Test
    fun `Should get data with success`() = runTest {
        whenever(useCase()).thenReturn(true)

        viewModel = SplashViewModel(useCase, rule.dispatcher)

        viewModel.state.value.let {
            assertTrue(it is States.Success)
        }
    }
}
