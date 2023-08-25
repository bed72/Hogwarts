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
import com.bed.test.factories.authentication.SignUpFactory

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

    private lateinit var factory: SignUpFactory

    private lateinit var signUpViewModel: SignUpViewModel

    @Before
    fun setUp() {
        factory = SignUpFactory()
        signUpViewModel = SignUpViewModel(
            signUpUseCase,
            rule.dispatcher,
            saveStorageUseCase
        ).apply { states.observeForever(observer) }
    }

    @Test
    fun `Should emit Loading State when trying to sign up with return success`() = runTest {
        whenever(signUpUseCase(any())).thenReturn(flowOf(factory.success))

        signUpViewModel.signUp(factory.signUpParameter)

        verify(observer).onChanged(isA<SignUpViewModel.States.Loading>())
        verify(observer).onChanged(isA<SignUpViewModel.States.Success>())
    }

    @Test
    fun `Should emit Loading State when trying to sign up with return failure`() = runTest {
        whenever(signUpUseCase(any())).thenReturn(flowOf(factory.failure))

        signUpViewModel.signUp(factory.signUpParameter)

        verify(observer).onChanged(isA<SignUpViewModel.States.Loading>())
        verify(observer).onChanged(isA<SignUpViewModel.States.Failure>())
    }

    @Test
    fun `Should return AuthenticationModel in Success State when trying to sign up with return success`() =
        runTest {
            whenever(signUpUseCase(any())).thenReturn(flowOf(factory.success))

            signUpViewModel.signUp(factory.signUpParameter)

            val (success) = signUpViewModel.states.value as SignUpViewModel.States.Success
            assertEquals(success.expireIn, 3600)
            assertEquals(success.accessToken, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.refreshToken, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.user.email, "bed@email.com")
            assertEquals(success.user.userMetadata.name, "Bed")
        }

    @Test
    fun `Should return Failure State when trying to create an account with return failure`() = runTest {
        whenever(signUpUseCase(any())).thenReturn(flowOf(factory.failure))

        signUpViewModel.signUp(factory.signUpParameter)

        val (failure) = signUpViewModel.states.value as SignUpViewModel.States.Failure
        assertEquals(failure, "Este e-mail já foi cadastrado.")
    }
}
