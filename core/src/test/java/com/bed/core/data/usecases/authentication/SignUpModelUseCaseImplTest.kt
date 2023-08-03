package com.bed.core.data.usecases.authentication

import arrow.core.Either

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.test.rule.MainCoroutineRule
import com.bed.test.factories.authentication.SingUpFactory

import com.bed.core.domain.models.authentication.SignUpModel

import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.core.usecases.authentication.SignUpUseCaseImpl

import com.bed.core.data.repositories.authentication.AuthenticationRepository

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class SignUpModelUseCaseImplTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @Mock
    private lateinit var repository: AuthenticationRepository

    private lateinit var useCase: SignUpUseCase

    private lateinit var factory: SingUpFactory

    @Before
    fun setUp() {
        factory = SingUpFactory()
        useCase = SignUpUseCaseImpl(rule.testDispatcherProvider, repository)
    }

    @Test
    fun `Should return value not null when trying create account`() = runTest {
        whenever(repository(any())).thenReturn(factory.create(SingUpFactory.Mock.Failure))

        val response = useCase(factory.params).first()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository once when trying create account`() = runTest {
        whenever(repository(any())).thenReturn(factory.create(SingUpFactory.Mock.Failure))

        useCase(factory.params).first()

        verify(repository, times(1))(any())
    }

    @Test
    fun `Should return failure value when trying a create account`() = runTest {
        whenever(repository(any())).thenReturn(factory.create(SingUpFactory.Mock.Failure))

        val response = useCase(factory.params).first()

        assertTrue(response is Either.Left<Message>)
    }

    @Test
    fun `Should return failure value with status and message when trying a create account`() = runTest {
        whenever(repository(any())).thenReturn(factory.create(SingUpFactory.Mock.Failure))

        val response = useCase(factory.params).first()

        response.onLeft { (message) ->
            assertEquals(message, "Este e-mail já foi cadastrado!")
        }
    }

    @Test
    fun `Should return success value when trying a create account`() = runTest {
        whenever(repository(any())).thenReturn(factory.create(SingUpFactory.Mock.Success))

        val response = useCase(factory.params).first()

        assertTrue(response is Either.Right<SignUpModel>)
    }

    @Test
    fun `Should return success value with status and message when trying a create account`() = runTest {
        whenever(repository(any())).thenReturn(factory.create(SingUpFactory.Mock.Success))

        val response = useCase(factory.params).first()

        response.onRight { (name, email) ->
            assertEquals(name, "Bed")
            assertEquals(email, "email@email.com")
        }
    }
}
