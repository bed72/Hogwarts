package com.bed.seller.presentation.ui.splash

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

import org.mockito.Mock
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

import com.bed.core.usecases.authentication.IsLoggedInUseCase

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class SplashViewModelTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var isLoggedInUseCase: IsLoggedInUseCase

    @Mock
    private lateinit var observer: Observer<SplashViewModel.States>

    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun setUp() {
        splashViewModel = SplashViewModel(
            rule.dispatcher,
            isLoggedInUseCase
        ).apply { states.observeForever(observer) }
    }

    @Test
    fun `Should issue the Loading Status when verifying that you are logged in with a successful return`() = runTest {
        whenever(isLoggedInUseCase()).thenReturn(flowOf(true))

        splashViewModel.isLoggedIn()

        verify(observer).onChanged(isA<SplashViewModel.States.Loading>())
        verify(observer).onChanged(isA<SplashViewModel.States.IsLoggedIn>())
    }

    @Test
    fun `Should issue the Loading Status when verifying that you are logged in with a failure return`() = runTest {
        whenever(isLoggedInUseCase()).thenReturn(flowOf(false))

        splashViewModel.isLoggedIn()

        verify(observer).onChanged(isA<SplashViewModel.States.Loading>())
        verify(observer).onChanged(isA<SplashViewModel.States.IsLoggedIn>())
    }

    @Test
    fun `Should return true in Success State when trying is logged in with return success`() =
        runTest {
            whenever(isLoggedInUseCase()).thenReturn(flowOf(true))

            splashViewModel.isLoggedIn()

            val (success) = splashViewModel.states.value as SplashViewModel.States.IsLoggedIn
            assertEquals(true, success)
        }

    @Test
    fun `Should return true in Success State when trying is logged in with return failure`() =
        runTest {
            whenever(isLoggedInUseCase()).thenReturn(flowOf(false))

            splashViewModel.isLoggedIn()

            val (success) = splashViewModel.states.value as SplashViewModel.States.IsLoggedIn
            assertEquals(false, success)
        }
}
