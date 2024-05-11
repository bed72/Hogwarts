package com.bed.seller.presentation.ui.splash

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertEquals

import org.mockito.Mock
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.test.rules.MainCoroutineRule
import com.bed.test.factories.authentication.Factories

import com.bed.core.usecases.authentication.IsLoggedInUsecase

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class SplashViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @Mock
    private lateinit var useCase: IsLoggedInUsecase

    private lateinit var viewModel: SplashViewModel

    private lateinit var states: MutableList<SplashViewModel.States>

    @Before
    fun setUp() {
        states = mutableListOf()
        viewModel = SplashViewModel(useCase)
    }

    @Test
    fun `Should issue first loading state when verify is logged with successful return`() = runTest {
        whenever(useCase()).thenReturn(true)

        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(states) }

        viewModel.isLoggedIn()

        assertEquals(states[Factories.INITIAL], SplashViewModel.States.Initial)
        states[IS_LOGGED].let { assertTrue(it is SplashViewModel.States.IsLoggedIn && it.isLogged) }

        job.cancel()
    }

    @Test
    fun `Should issue first loading state when verify is logged with failure return`() = runTest {
        whenever(useCase()).thenReturn(false)

        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(states) }

        viewModel.isLoggedIn()

        assertEquals(states[Factories.INITIAL], SplashViewModel.States.Initial)
        states[IS_LOGGED].let { assertFalse(it is SplashViewModel.States.IsLoggedIn && it.isLogged) }

        job.cancel()
    }

    @Test
    fun `Should verify is logged with failure return`() = runTest {
        whenever(useCase()).thenReturn(false)

        viewModel.isLoggedIn()

        viewModel.state.value.let {
            it as SplashViewModel.States.IsLoggedIn
            assertFalse(it.isLogged)
        }
    }

    @Test
    fun `Should verify is logged with success return`() = runTest {
        whenever(useCase()).thenReturn(true)

        viewModel.isLoggedIn()

        viewModel.state.value.let {
            it as SplashViewModel.States.IsLoggedIn
            assertTrue(it.isLogged)
        }
    }

    companion object {
        private const val IS_LOGGED = 1
    }
}
