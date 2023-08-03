package com.bed.seller.presentation.ui.authentication

import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.mockito.Mock
import org.junit.runner.RunWith

import androidx.lifecycle.Observer

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever

import org.mockito.junit.MockitoJUnitRunner

import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bed.test.rule.MainCoroutineRule
import com.bed.test.factories.authentication.SingUpFactory

import com.bed.core.domain.models.authentication.SignUpModel

import com.bed.core.usecases.storage.SaveStorageUseCase
import com.bed.core.usecases.authentication.SignUpUseCase

import com.bed.seller.presentation.ui.authentication.signup.SignUpViewModel
import com.bed.seller.presentation.ui.authentication.signup.states.SignUpLiveData

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
    private lateinit var observer: Observer<SignUpLiveData.States>

    private lateinit var factory: SingUpFactory

    private lateinit var signUpViewModel: SignUpViewModel

    @Before
    fun setUp() {
        factory = SingUpFactory()
        signUpViewModel = SignUpViewModel(
            signUpUseCase,
            rule.testDispatcherProvider,
            saveStorageUseCase
        ).apply { auth.states.observeForever(observer) }
    }

    @Test
    fun `Should emit Loading State when trying to create an account`() = runTest {
            whenever(signUpUseCase(any())).thenReturn(flowOf(factory.successEntity))

            signUpViewModel.auth.magicLink(factory.params)

            verify(observer).onChanged(isA<SignUpLiveData.States.Loading>())
            verify(observer).onChanged(isA<SignUpLiveData.States.Success>())
        }

    @Test
    fun `Should return MagicLinkEntity in Success State when trying to create an account with returns success`() =
        runTest {
            whenever(signUpUseCase(any())).thenReturn(flowOf(factory.successEntity))

            signUpViewModel.auth.magicLink(factory.params)

            val (data) = signUpViewModel.auth.states.value as SignUpLiveData.States.Success
            assertEquals(data, isA<SignUpModel>())
        }

    @Test
    fun `Should return Failure State when trying to create an account with returns failure`() = runTest {
            whenever(signUpUseCase(any())).thenReturn(flowOf(factory.failureEntity))

            signUpViewModel.auth.magicLink(factory.params)

            val (data) = signUpViewModel.auth.states.value as SignUpLiveData.States.Failure
            assertEquals(data, "Este e-mail já foi cadastrado!")
        }
}
