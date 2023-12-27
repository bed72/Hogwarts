package com.bed.core.usecases.authentication

import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull

import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.core.data.repositories.AuthenticationRepository

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class IsLoggedInUseCaseImplTest {
    private lateinit var useCase: IsLoggedInUseCase

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setup() {
        useCase = IsLoggedInUseCaseImpl(repository)
    }

    @Test
    fun `Should return value not null when trying verify is logged in account with failure return`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(false)

        val response = useCase()

        assertNotNull(response)
    }

    @Test
    fun `Should return value not null when trying verify is logged in account with successful return`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(true)

        val response = useCase()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository once when trying verify is logged in account with failure return`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(false)

        useCase()

        verify(repository, times(1)).isLoggedIn()
    }

    @Test
    fun `Should only call repository once when trying verify is logged in account with successful return`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(true)

        useCase()

        verify(repository, times(1)).isLoggedIn()
    }

    @Test
    fun `Should return failure value when trying verify is logged in account`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(false)

        val response = useCase()

        assertEquals(false, response)
    }

    @Test
    fun `Should return success value when trying verify is logged in account`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(true)

        val response = useCase()

        assertEquals(true, response)
    }
}
