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

import com.bed.test.rules.MainCoroutineRule

import com.bed.core.data.repositories.AuthenticationRepository

import com.bed.test.factories.authentication.AuthenticationFactory

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.AuthenticationModel

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class SignUpUseCaseImplTest {
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
    fun `Should return value not null when trying sign up account with failure return`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.signInAndSingUpValidParameter).first()

        assertNotNull(response)
    }

    @Test
    fun `Should return value not null when trying sign up account with successful return`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.success)

        val response = useCase(factory.signInAndSingUpValidParameter).first()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository once when trying sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        useCase(factory.signInAndSingUpValidParameter).first()

        verify(repository, times(1)).signUp(any())
    }

    @Test
    fun `Should return failure value when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.signInAndSingUpValidParameter).first()

        assertTrue(response is Either.Left<MessageModel>)
    }

    @Test
    fun `Should return failure value with status and message when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.failure)

        val response = useCase(factory.signInAndSingUpValidParameter).first()

        response.onLeft { failure ->
            assertEquals("Ops, um erro aconteceu.", failure.message)
        }
    }

    @Test
    fun `Should return success value when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.success)

        val response = useCase(factory.signInAndSingUpValidParameter).first()

        assertTrue(response is Either.Right<AuthenticationModel>)
    }

    @Test
    fun `Should return success value with status and message when trying a sign up account`() = runTest {
        whenever(repository.signUp(any())).thenReturn(factory.success)

        val response = useCase(factory.signInAndSingUpValidParameter).first()

        response.onRight { success ->
            assertEquals("5CQcsREkB5xcqbY1L...", success.uid)
            assertEquals("Gabriel Ramos", success.name)
            assertEquals("bed@gmail.com", success.email)
            assertEquals("https://github.com/bed72.png", success.photo )
            assertEquals(false, success.emailVerified)
        }
    }
}
