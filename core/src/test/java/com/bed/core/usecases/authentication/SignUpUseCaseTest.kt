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

import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.test.factories.authentication.AuthenticationFactory

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.AuthenticationModel

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class SignUpUseCaseTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var factory: AuthenticationFactory

    private lateinit var useCase: SignUpUseCase

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setup() {
        factory = AuthenticationFactory()
        useCase = SignUpUseCaseImpl(rule.dispatcher, repository)
    }

    @Test
    fun `Should return value not null when trying sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.authenticationParameter).first()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository once when trying sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        useCase(factory.authenticationParameter).first()

        verify(repository, times(1)).signUp(any())
    }

    @Test
    fun `Should return failure value when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.authenticationParameter).first()

        assertTrue(response is Either.Left<MessageModel>)
    }

    @Test
    fun `Should return failure value with status and message when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.authenticationParameter).first()

        response.onLeft { failure ->
            assertEquals(failure.message, "Ops, um erro aconteceu.")
        }
    }

    @Test
    fun `Should return success value when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.success)

        val response = useCase(factory.authenticationParameter).first()

        assertTrue(response is Either.Right<AuthenticationModel>)
    }

    @Test
    fun `Should return success value with status and message when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.success)

        val response = useCase(factory.authenticationParameter).first()

        response.onRight { success ->
            assertEquals(success.uid, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.name, "Gabriel Ramos")
            assertEquals(success.email, "bed@gmail.com")
            assertEquals(success.photo, "https://github.com/bed72.png")
            assertEquals(success.emailVerified, false)
        }
    }
}
