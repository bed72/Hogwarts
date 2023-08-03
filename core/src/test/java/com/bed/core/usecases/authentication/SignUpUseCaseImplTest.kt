package com.bed.core.usecases.authentication

import arrow.core.Either

import org.junit.Rule
import org.junit.Test
import org.junit.Before

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK

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

import com.bed.core.data.repositories.authentication.AuthenticationRepository

@ExperimentalCoroutinesApi
internal class SignUpUseCaseTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var useCase: SignUpUseCase

    private lateinit var factory: SignUpFactory

    @MockK(relaxUnitFun = true)
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        factory = SignUpFactory()
        useCase = SignUpUseCaseImpl(rule.dispatcher, repository)
    }

    @Test
    fun `Should return value not null when trying sign up account`() = runTest {
        coEvery { repository.signUp(any()) } returns  factory.failure

        val response = useCase(factory.validParams).first()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository once when trying sign up account`() = runTest {
        coEvery { repository.signUp(any()) } returns  factory.failure

        useCase(factory.validParams).first()

        coVerify(exactly = 1) { repository.signUp(any()) }
    }

    @Test
    fun `Should return failure value when trying a sign up account`() = runTest {
        coEvery { repository.signUp(any()) } returns  factory.failure

        val response = useCase(factory.validParams).first()

        assertTrue(response is Either.Left<MessageModel>)
    }

    @Test
    fun `Should return failure value with status and message when trying a sign up account`() = runTest {
        coEvery { repository.signUp(any()) } returns  factory.failure

        val response = useCase(factory.validParams).first()

        response.onLeft { failure ->
            assertEquals(failure.error, "Este e-mail já foi cadastrado.")
        }
    }

    @Test
    fun `Should return success value when trying a sign up account`() = runTest {
        coEvery { repository.signUp(any()) } returns  factory.success

        val response = useCase(factory.validParams).first()

        assertTrue(response is Either.Right<AuthenticationModel>)
    }

    @Test
    fun `Should return success value with status and message when trying a sign up account`() = runTest {
        coEvery { repository.signUp(any()) } returns  factory.success

        val response = useCase(factory.validParams).first()

        response.onRight { success ->
            assertEquals(success.expireIn, 3600)
            assertEquals(success.accessToken, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.refreshToken, "5CQcsREkB5xcqbY1L...")
            assertEquals(success.user.email, "bed@email.com")
            assertEquals(success.user.userMetadata.name, "Bed")
        }
    }

}
