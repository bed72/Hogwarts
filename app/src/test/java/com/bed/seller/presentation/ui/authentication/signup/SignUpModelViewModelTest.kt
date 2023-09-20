package com.bed.seller.presentation.ui.authentication.signup

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import junit.framework.TestCase.assertEquals

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

import com.bed.core.usecases.storage.SaveStorageUseCase
import com.bed.core.usecases.authentication.SignUpUseCase

import com.bed.test.rule.MainCoroutineRule
import com.bed.test.factories.AuthenticationFactory

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class SignUpModelViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var signUpUseCase: SignUpUseCase

    @Mock
    private lateinit var saveStorageUseCase: SaveStorageUseCase

    @Mock
    private lateinit var observer: Observer<SignUpViewModel.States>

    private lateinit var factory: AuthenticationFactory

    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
        viewModel = SignUpViewModel(
            signUpUseCase,
            rule.dispatcher,
            saveStorageUseCase
        ).apply { states.observeForever(observer) }
    }

    @Test
    fun `Should emit Loading State when trying to sign up with return success`() = runTest {
        whenever(signUpUseCase(any())).thenReturn(flowOf(factory.success))

        viewModel.signUp(factory.signInAndSingUpValidParameter)

        verify(observer).onChanged(isA<SignUpViewModel.States.Loading>())
        verify(observer).onChanged(isA<SignUpViewModel.States.Success>())
    }

    @Test
    fun `Should emit Loading State when trying to sign up with return failure`() = runTest {
        whenever(signUpUseCase(any())).thenReturn(flowOf(factory.failure))

        viewModel.signUp(factory.signInAndSingUpValidParameter)

        verify(observer).onChanged(isA<SignUpViewModel.States.Loading>())
        verify(observer).onChanged(isA<SignUpViewModel.States.Failure>())
    }

    @Test
    fun `Should return AuthenticationModel in Success State when trying to sign up with return success`() =
        runTest {
            whenever(signUpUseCase(any())).thenReturn(flowOf(factory.success))

            viewModel.signUp(factory.signInAndSingUpValidParameter)

            val (success) = viewModel.states.value as SignUpViewModel.States.Success
            assertEquals("5CQcsREkB5xcqbY1L...", success.uid)
            assertEquals("Gabriel Ramos", success.name)
            assertEquals("bed@gmail.com", success.email)
            assertEquals("https://github.com/bed72.png", success.photo)
            assertEquals(false, success.emailVerified)
        }

    @Test
    fun `Should return MessageModel in Failure State when trying to create an account with return failure`() = runTest {
        whenever(signUpUseCase(any())).thenReturn(flowOf(factory.failure))

        viewModel.signUp(factory.signInAndSingUpValidParameter)

        val (failure) = viewModel.states.value as SignUpViewModel.States.Failure
        assertEquals("Ops, um erro aconteceu.", failure)
    }
}
