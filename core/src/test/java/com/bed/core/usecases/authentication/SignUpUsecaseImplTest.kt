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

import com.bed.core.entities.output.MessageOutput
import com.bed.core.entities.output.AuthenticationOutput
import com.bed.core.repositories.AuthenticationRepository

import com.bed.test.rules.MainCoroutineRule
import com.bed.test.factories.authentication.AuthenticationFactory

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class SignUpUsecaseImplTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var useCase: SignUpUsecase
    private lateinit var factory: AuthenticationFactory

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setup() {
        factory = AuthenticationFactory()
        useCase = SignUpUsecaseImpl(rule.dispatcher, repository)
    }

    @Test
    fun `Should only call repository once when trying sign up account with true return`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.success)

        useCase(factory.signInAndSingUpValidParameter).first()

        verify(repository, times(1)).signUp(any())
    }

    @Test
    fun `Should only call repository once when trying sign up account with false return`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        useCase(factory.signInAndSingUpValidParameter).first()

        verify(repository, times(1)).signUp(any())
    }

    @Test
    fun `Should return success value with status and message when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.success)

        val response = useCase(factory.signInAndSingUpValidParameter).first()

        assertTrue(response is Either.Right<AuthenticationOutput>)
        response.onRight { success ->
            assertNotNull(success)
            assertEquals(success.emailVerified, false)
            assertEquals(success.name, "Gabriel Ramos")
            assertEquals(success.email, "bed@gmail.com")
            assertEquals(success.uid, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.photo, "https://github.com/bed72.png")
        }
    }

    @Test
    fun `Should return failure value with status and message when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.signInAndSingUpValidParameter).first()

        assertTrue(response is Either.Left<MessageOutput>)
        response.onLeft { failure ->
            assertNotNull(failure)
            assertEquals(failure.message, "Ops, um erro aconteceu.")
        }
    }
}
