package com.bed.seller.presentation.ui.authentication.signup

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertEquals

import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.seller.presentation.commons.states.States

import com.bed.test.rules.MainCoroutineRule
import com.bed.test.factories.authentication.Factories
import com.bed.test.factories.authentication.AuthenticationFactory

import com.bed.core.usecases.authentication.SignUpUsecase
import com.bed.core.entities.output.AuthenticationOutput

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class SignUpViewModelTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @Mock
    private lateinit var useCase: SignUpUsecase

    private lateinit var viewModel: SignUpViewModel
    private lateinit var factory: AuthenticationFactory
    private lateinit var states: MutableList<States<AuthenticationOutput>>

    @Before
    fun setUp() {
        states = mutableListOf()
        factory = AuthenticationFactory()
        viewModel = SignUpViewModel(useCase)
    }

    @Test
    fun `Should emit Loading State when trying to sign up with successful return`() = runTest {
        whenever(useCase(any())).thenReturn(factory.success)

        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(states) }

        viewModel.signUp(factory.signInAndSingUpValidParameter)

        assertEquals(states[Factories.INITIAL], States.Initial)
        assertEquals(states[Factories.LOADING], States.Loading)
        assertTrue(states[Factories.SUCCESS] is States.Success)

        job.cancel()
    }

    @Test
    fun `Should emit Loading State when trying to sign up with failure return`() = runTest {
        whenever(useCase(any())).thenReturn(factory.failure)

        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(states) }

        viewModel.signUp(factory.signInAndSingUpValidParameter)

        assertEquals(states[Factories.INITIAL], States.Initial)
        assertEquals(states[Factories.LOADING], States.Loading)
        assertTrue(states[Factories.FAILURE] is States.Failure)

        job.cancel()
    }
    @Test
    fun `Should return AuthenticationModel in Success State when trying to sign up with successful return`() =
        runTest {
            whenever(useCase(any())).thenReturn(factory.success)

            viewModel.signUp(factory.signInAndSingUpValidParameter)

            val (success) = viewModel.state.value as States.Success
            assertEquals(success.emailVerified, false)
            assertEquals(success.name, "Gabriel Ramos")
            assertEquals(success.email, "bed@gmail.com")
            assertEquals(success.uid, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.photo, "https://github.com/bed72.png")
        }


        @Test
        fun `Should return MessageModel in Failure State when trying to create an account with failure return`() = runTest {
            whenever(useCase(any())).thenReturn(factory.failure)

            viewModel.signUp(factory.signInAndSingUpValidParameter)

            val (failure) = viewModel.state.value as States.Failure
            assertEquals("Ops, um erro aconteceu.", failure)
        }
}
