package com.bed.core.usecases.authentication

import arrow.core.Either

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.test.rule.MainCoroutineRule
import com.bed.test.factories.authentication.SignUpFactory

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.AuthenticationModel

import com.bed.core.data.repositories.AuthenticationRepository

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class SignUpUseCaseTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var factory: SignUpFactory

    private lateinit var useCase: SignUpUseCase

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setup() {
        factory = SignUpFactory()
        useCase = SignUpUseCaseImpl(rule.dispatcher, repository)
    }

    @Test
    fun `Should return value not null when trying sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.signUpParameter).first()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository once when trying sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        useCase(factory.signUpParameter).first()

        verify(repository, times(1)).signUp(any())
    }

    @Test
    fun `Should return failure value when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.signUpParameter).first()

        assertTrue(response is Either.Left<MessageModel>)
    }

    @Test
    fun `Should return failure value with status and message when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.signUpParameter).first()

        response.onLeft { failure ->
            assertEquals(failure.message, "Este e-mail já foi cadastrado.")
        }
    }

    @Test
    fun `Should return success value when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.success)

        val response = useCase(factory.signUpParameter).first()

        assertTrue(response is Either.Right<AuthenticationModel>)
    }

    @Test
    fun `Should return success value with status and message when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.success)

        val response = useCase(factory.signUpParameter).first()

        response.onRight { success ->
            assertEquals(success.expireIn, 3600)
            assertEquals(success.accessToken, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.refreshToken, "5CQcsREkB5xcqbY1L..")
            assertEquals(success.user.email, "bed@email.com")
            assertEquals(success.user.userMetadata.name, "Bed")
        }
    }
}
