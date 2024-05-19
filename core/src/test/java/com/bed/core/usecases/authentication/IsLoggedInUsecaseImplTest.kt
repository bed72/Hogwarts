package com.bed.core.usecases.authentication

import org.junit.Test
import org.junit.Rule
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

import com.bed.core.repositories.AuthenticationRepository
import com.bed.test.rules.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class IsLoggedInUsecaseImplTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var usecase: IsLoggedInUsecase

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setup() {
        usecase = IsLoggedInUsecaseImpl(repository)
    }

    @Test
    fun `Should only call repository once when trying verify is logged in account with successful return`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(true)

        usecase()

        verify(repository, times(1)).isLoggedIn()
    }

    @Test
    fun `Should only call repository once when trying verify is logged in account with failure return`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(false)

        usecase()

        verify(repository, times(1)).isLoggedIn()
    }

    @Test
    fun `Should return success value when trying verify is logged in account`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(true)

        usecase().run {
            assertNotNull(this)
            assertEquals(this, true)
        }
    }

    @Test
    fun `Should return failure value when trying verify is logged in account`() = runTest {
        whenever(repository.isLoggedIn()).thenReturn(false)

        usecase().run {
            assertNotNull(this)
            assertEquals(this, false)
        }
    }
}
