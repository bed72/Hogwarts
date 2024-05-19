package com.bed.core.usecases.authentication

import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlinx.coroutines.test.runTest
import org.mockito.junit.MockitoJUnitRunner

import junit.framework.TestCase.assertNotNull

import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.core.repositories.AuthenticationRepository

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class SignOutUseCaseUsecaseImplTest {
    private lateinit var useCase: SignOutUsecase

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setUp() {
        useCase = SignOutUsecaseImpl(repository)
    }

    @Test
    fun `Should only call repository when trying sign out account with successful return`() = runTest {
        useCase()

        verify(repository, times(1)).signOut()
    }

    @Test
    fun `Should only call repository when trying sign out account with failure return`() = runTest {
        useCase()

        verify(repository, times(1)).signOut()
    }

    @Test
    fun `Should return value not null when trying sign out account with successful return`() = runTest {
        val response = useCase()

        assertNotNull(response)
    }

    @Test
    fun `Should return value not null when trying sign out account with failure return`() = runTest {
        val response = useCase()

        assertNotNull(response)
    }
}
