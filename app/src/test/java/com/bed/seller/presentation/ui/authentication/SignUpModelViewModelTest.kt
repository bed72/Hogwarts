package com.bed.seller.presentation.ui.authentication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.seller.presentation.ui.authentication.signup.SignUpViewModel
import com.bed.seller.presentation.ui.authentication.signup.states.SignUpLiveData
import com.bed.test.factories.authentication.SignUpFactory
import com.bed.test.rule.MainCoroutineRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.isA
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

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
    private lateinit var observer: Observer<SignUpLiveData.States>

    private lateinit var factory: SignUpFactory

    private lateinit var signUpViewModel: SignUpViewModel

    @Before
    fun setUp() {
        factory = SignUpFactory()
        signUpViewModel = SignUpViewModel(
            signUpUseCase,
            rule.dispatcher,
        ).apply { auth.states.observeForever(observer) }
    }

    @Test
    fun `Should emit Loading State when trying to create an account with success`() = runTest {
        whenever(signUpUseCase(any())).thenReturn((flowOf(factory.success)))

        signUpViewModel.auth.signUp(factory.validParams)

        verify(observer).onChanged(isA<SignUpLiveData.States.Loading>())
        verify(observer).onChanged(isA<SignUpLiveData.States.Success>())
    }

    @Test
    fun `Should emit Loading State when trying to create an account with failure`() = runTest {
        whenever(signUpUseCase(any())).thenReturn((flowOf(factory.failure)))

        signUpViewModel.auth.signUp(factory.validParams)

        verify(observer).onChanged(isA<SignUpLiveData.States.Loading>())
        verify(observer).onChanged(isA<SignUpLiveData.States.Failure>())
    }

    @Test
    fun `Should return AuthenticationModel in Success State when trying to create an account with returns success`() =
        runTest {
            whenever(signUpUseCase(any())).thenReturn(flowOf(factory.success))

            signUpViewModel.auth.signUp(factory.validParams)

            val (success) = signUpViewModel.auth.states.value as SignUpLiveData.States.Success
            assertEquals(success.expireIn, 3600)
            assertEquals(success.accessToken, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.refreshToken, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.user.email, "bed@email.com")
            assertEquals(success.user.userMetadata.name, "Bed")
        }

    @Test
    fun `Should return Failure State when trying to create an account with returns failure`() = runTest {
        whenever(signUpUseCase(any())).thenReturn(flowOf(factory.failure))

        signUpViewModel.auth.signUp(factory.validParams)

        val (failure) = signUpViewModel.auth.states.value as SignUpLiveData.States.Failure
        assertEquals(failure, "")
    }
}
