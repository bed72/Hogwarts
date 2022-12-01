package com.bed.seller.presentation.ui.auth.signup

import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.mockito.Mock
import org.junit.runner.RunWith

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever

import org.mockito.junit.MockitoJUnitRunner
import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.lifecycle.Observer
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bed.seller.data.usecases.mocks.CommonMock
import com.bed.seller.data.usecases.auth.mocks.AuthMock

import com.bed.seller.domain.usecases.auth.SignUpUseCase
import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.infrastructure.rules.MainCoroutineRule
import com.bed.seller.infrastructure.network.models.auth.toEntity

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.splash.states.SplashLiveData

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var commons: Commons

    @Mock
    lateinit var signUpUseCase: SignUpUseCase

    @Mock
    lateinit var validatorUseCase: ValidatorUseCase

    @Mock
    lateinit var statesObserver: Observer<SplashLiveData.States>

    private lateinit var signUpViewModel: SignUpViewModel

    private val authMock = AuthMock()

    @Before
    fun setUp() {
        signUpViewModel = SignUpViewModel(
            commons,
            validatorUseCase,
            signUpUseCase,
            mainCoroutineRule.testDispatcherProvider
        ).apply {
            auth.state.observeForever(statesObserver)
        }
    }

    @Test
    fun `Should emit Loading State when trying to create an account`() =
        runTest {
            whenever(signUpUseCase(any()))
                .thenReturn(flowOf(authMock.successEntity))

            signUpViewModel.auth.signUp(CommonMock.PARAMS_SIGN_UP_REQUEST)

            verify(statesObserver).onChanged(isA<SplashLiveData.States.Loading>())
        }

    @Test
    fun `Should return SignUpEntity in Success State when trying to create an account with returns success`() =
        runTest {
            whenever(signUpUseCase(any()))
                .thenReturn(flowOf(authMock.successEntity))

            signUpViewModel.auth.signUp(CommonMock.PARAMS_SIGN_UP_REQUEST)

            val (data) = signUpViewModel.auth.state.value as SplashLiveData.States.Success
            assertEquals(data, authMock.authSuccessModel.toEntity())
        }

    @Test
    fun `Should return Failure State when trying to create an account with returns failure`() =
        runTest {
            whenever(signUpUseCase(any()))
                .thenReturn(flowOf(authMock.failureEntity))

            whenever(commons.mapper(any()))
                .thenReturn(authMock.stringIdFailureBedRequest)

            signUpViewModel.auth.signUp(CommonMock.PARAMS_SIGN_UP_REQUEST)

            val data = signUpViewModel.auth.state.value as SplashLiveData
            .States.Failure
            assertEquals(data.message, authMock.intIdFailureBedRequest)
        }
}
