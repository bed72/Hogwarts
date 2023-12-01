package com.bed.seller.presentation.ui.authentication.signin

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
import com.bed.core.usecases.authentication.SignInUseCase

import com.bed.test.rule.MainCoroutineRule
import com.bed.test.factories.authentication.SignInFactory

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class SignInViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var signInUseCase: SignInUseCase

    @Mock
    private lateinit var storageUseCase: SaveStorageUseCase

    @Mock
    private lateinit var observer: Observer<SignInViewModel.States>

    private lateinit var factory: SignInFactory

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        factory = SignInFactory()
        viewModel = SignInViewModel(
            signInUseCase,
            rule.dispatcher,
            storageUseCase
        ).apply { states.observeForever(observer) }
    }

    @Test
    fun `Should emit Loading State when trying to sign in with return success`() = runTest {
        whenever(signInUseCase(any())).thenReturn(flowOf(factory.success))

        viewModel.signIn(factory.signInParameter)

        verify(observer).onChanged(isA<SignInViewModel.States.Loading>())
        verify(observer).onChanged(isA<SignInViewModel.States.Success>())
    }

    @Test
    fun `Should emit Loading State when trying to sign in with return failure`() = runTest {
        whenever(signInUseCase(any())).thenReturn(flowOf(factory.failure))

        viewModel.signIn(factory.signInParameter)

        verify(observer).onChanged(isA<SignInViewModel.States.Loading>())
        verify(observer).onChanged(isA<SignInViewModel.States.Failure>())
    }

    @Test
    fun `Should return AuthenticationModel in Success State when trying to sign in with return success`() =
        runTest {
            whenever(signInUseCase(any())).thenReturn(flowOf(factory.success))

            viewModel.signIn(factory.signInParameter)

            val (success) = viewModel.states.value as SignInViewModel.States.Success
            assertEquals(success.expireIn, 3600)
            assertEquals(success.accessToken, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.refreshToken, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.user.email, "bed@email.com")
            assertEquals(success.user.userMetadata.name, "Bed")
        }

    @Test
    fun `Should return Failure State when trying to create an account with returns failure`() = runTest {
        whenever(signInUseCase(any())).thenReturn(flowOf(factory.failure))

        viewModel.signIn(factory.signInParameter)

        val (failure) = viewModel.states.value as SignInViewModel.States.Failure
        assertEquals(failure, "Credenciais inválidas.")
    }
}
