package com.bed.seller.data.usecases.auth.signup

import arrow.core.Either

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.seller.data.client.storage.StorageClient
import com.bed.seller.data.client.auth.SignUpClient

import com.bed.seller.data.usecases.mocks.CommonMock
import com.bed.seller.data.usecases.auth.mocks.AuthMock
import com.bed.seller.data.usecases.auth.RemoteSignUpUseCase

import com.bed.seller.infrastructure.rules.MainCoroutineRule
import com.bed.seller.infrastructure.network.models.auth.toEntity
import com.bed.seller.infrastructure.network.models.failure.toEntity

import com.bed.seller.domain.entities.ResponseEntity
import com.bed.seller.domain.usecases.auth.SignUpUseCase
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.failure.MessageFailureResponseEntity

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RemoteSignUpRemoteUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var storageClient: StorageClient

    @Mock
    private lateinit var signUpClient: SignUpClient

    private lateinit var signUpUseCase: SignUpUseCase

    private val authMock = AuthMock()

    @Before
    fun setUp() {
        signUpUseCase = RemoteSignUpUseCase(
            storageClient,
            signUpClient,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `Should call SignUpClient when trying to create an account and the return cannot be null`() =
        runTest {
            whenever(signUpClient(authMock.pathSignUp, CommonMock.PARAMS_SIGN_UP_REQUEST))
                .thenReturn(authMock.successModel)

            val response = signUpUseCase(authMock.paramsSignUp).first()

            assertNotNull(response)
        }

    @Test
    fun `Should call SignUpClient when trying to create an account`() = runTest {
        whenever(signUpClient(authMock.pathSignUp, CommonMock.PARAMS_SIGN_UP_REQUEST))
            .thenReturn(authMock.successModel)

        val response = signUpUseCase(authMock.paramsSignUp).first()

        assertNotNull(response)
        verify(signUpClient).invoke(authMock.pathSignUp, CommonMock.PARAMS_SIGN_UP_REQUEST)
    }

    @Test
    fun `Should call SignUpClient when trying to create an account and return Either the SignEntity`() =
        runTest {
            whenever(signUpClient(authMock.pathSignUp, CommonMock.PARAMS_SIGN_UP_REQUEST))
                .thenReturn(authMock.successModel)

            val response = signUpUseCase(authMock.paramsSignUp).first()

            assertTrue(response is Either.Right<ResponseEntity<AuthResponseEntity>>)
        }

    @Test
    fun `Should return Failure from Either when trying to create an account request with returns failure`() =
        runTest {
            whenever(signUpClient(authMock.pathSignUp, CommonMock.PARAMS_SIGN_UP_REQUEST))
                .thenReturn(authMock.failureModel)

            val response = signUpUseCase(authMock.paramsSignUp).first()

            assertTrue(response is Either.Left<ResponseEntity<MessageFailureResponseEntity>>)
        }

    @Test
    fun `Should return SignUpEntity in right Either when trying to create an account with returns success`() =
        runTest {
            whenever(signUpClient(authMock.pathSignUp, CommonMock.PARAMS_SIGN_UP_REQUEST))
                .thenReturn(authMock.successModel)

            signUpUseCase(authMock.paramsSignUp).collect { response ->
                response.fold(
                    { },
                    { entity ->
                        run {
                            assertNotNull(entity)
                            assertEquals(entity.status, 200)
                            assertEquals(entity.data, authMock.authSuccessModel.toEntity())
                        }
                    }
                )
            }
        }

    @Test
    fun `Should return HttpHandleException with type 400 when trying to create account with already registered email`() =
        runTest {
            whenever(signUpClient(authMock.pathSignUp, CommonMock.PARAMS_SIGN_UP_REQUEST))
                .thenReturn(authMock.failureModel)

            signUpUseCase(authMock.paramsSignUp).collect { response ->
                response.fold(
                    { failure ->
                        run {
                            assertNotNull(failure)
                            assertEquals(failure.status, 400)
                            assertEquals(failure.data, authMock.authFailureModel.toEntity())
                        }
                    },
                    { }
                )
            }
        }
}
