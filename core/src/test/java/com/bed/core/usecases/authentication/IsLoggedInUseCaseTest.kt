package com.bed.core.usecases.authentication

import org.junit.Rule
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

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.test.rule.MainCoroutineRule

import com.bed.core.data.repositories.AuthenticationRepository

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class IsLoggedInUseCaseTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var useCase: IsLoggedInUseCase

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setup() {
        useCase = IsLoggedInUseCaseImpl(rule.dispatcher, repository)
    }

    @Test
    fun `Should return value not null when trying verify is logged in account with failure`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(false)

        val response = useCase().first()

        assertNotNull(response)
    }

    @Test
    fun `Should return value not null when trying verify is logged in account with success`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(true)

        val response = useCase().first()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository once when trying verify is logged in account`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(false)

        useCase().first()

        verify(repository, times(1)).isLoggedIn()
    }

    @Test
    fun `Should return failure value when trying verify is logged in account with failure`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(false)

        val response = useCase().first()

        assertEquals(false, response)
    }

    @Test
    fun `Should return failure value when trying verify is logged in account with success`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(true)

        val response = useCase().first()

        assertEquals(true, response)
    }
}
