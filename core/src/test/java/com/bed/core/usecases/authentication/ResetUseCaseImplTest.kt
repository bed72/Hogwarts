package com.bed.core.usecases.authentication

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

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.test.rules.MainCoroutineRule

import com.bed.core.data.repositories.AuthenticationRepository
import com.bed.test.factories.authentication.AuthenticationFactory

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class ResetUseCaseImplTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var factory: AuthenticationFactory

    private lateinit var useCase: ResetUseCase

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
        useCase = ResetUseCaseImpl(rule.dispatcher, repository)
    }

    @Test
    fun `Should return value not null when trying reset password with failure return`() = runTest {
        whenever(repository.reset(any())).thenReturn(false)

        val response = useCase(factory.resetValidParameter).first()

        assertNotNull(response)
    }

    @Test
    fun `Should return value not null when trying reset password with successful return`() = runTest {
        whenever(repository.reset(any())).thenReturn(true)

        val response = useCase(factory.resetValidParameter).first()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository once when trying reset password`() = runTest {
        whenever(repository.reset(any())).thenReturn(false)

        useCase(factory.resetValidParameter).first()

        verify(repository, times(1)).reset(any())
    }

    @Test
    fun `Should return failure value when trying reset password`() = runTest {
        whenever(repository.reset(any())).thenReturn(false)

        val response = useCase(factory.resetValidParameter).first()

        assertEquals(false, response)
    }

    @Test
    fun `Should return success value when trying reset password`() = runTest {
        whenever(repository.reset(any())).thenReturn(true)

        val response = useCase(factory.resetValidParameter).first()

        assertEquals(true, response)
    }
}
