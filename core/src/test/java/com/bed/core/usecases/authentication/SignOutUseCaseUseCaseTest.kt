package com.bed.core.usecases.authentication

import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlinx.coroutines.test.runTest
import org.mockito.junit.MockitoJUnitRunner

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull

import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.core.data.repositories.AuthenticationRepository

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class SignOutUseCaseUseCaseTest {
    private lateinit var useCase: SignOutUseCase

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setUp() {
        useCase = SignOutUseCaseImpl(repository)
    }

    @Test
    fun `Should return value not null when trying sign out account with return failure`() = runTest {
        whenever(repository.signOut()).thenReturn(false)

        val response = useCase()

        assertNotNull(response)
    }

    @Test
    fun `Should return value not null when trying sign out account with return success`() = runTest {
        whenever(repository.signOut()).thenReturn(true)

        val response = useCase()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository when trying sign out account with return failure`() = runTest {
        whenever(repository.signOut()).thenReturn(false)

        useCase()

        verify(repository, times(1)).signOut()
    }

    @Test
    fun `Should only call repository when trying sign out account with return success`() = runTest {
        whenever(repository.signOut()).thenReturn(true)

        useCase()

        verify(repository, times(1)).signOut()
    }

    @Test
    fun `Should return failure value when trying sign out account`() = runTest {
        whenever(repository.signOut()).thenReturn(false)

        val response = useCase()

        assertEquals(false, response)
    }

    @Test
    fun `Should return success value when trying sign out account`() = runTest {
        whenever(repository.signOut()).thenReturn(true)

        val response = useCase()

        assertEquals(true, response)
    }
}
