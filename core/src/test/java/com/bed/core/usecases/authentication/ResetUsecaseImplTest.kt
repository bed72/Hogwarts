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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.core.repositories.AuthenticationRepository

import com.bed.test.rules.MainCoroutineRule
import com.bed.test.factories.authentication.AuthenticationFactory

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class ResetUsecaseImplTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var usecase: ResetUsecase
    private lateinit var factory: AuthenticationFactory

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
        usecase = ResetUsecaseImpl(rule.dispatcher, repository)
    }

    @Test
    fun `Should only call repository once when trying reset password with true return`() = runTest {
        whenever(repository.reset(any())).thenReturn(flowOf(true))

        usecase(factory.resetValidParameter)

        verify(repository, times(1)).reset(any())
    }

    @Test
    fun `Should only call repository once when trying reset password with false return`() = runTest {
        whenever(repository.reset(any())).thenReturn(flowOf(false))

        usecase(factory.resetValidParameter).first()

        verify(repository, times(1)).reset(any())
    }

    @Test
    fun `Should return success value when trying reset password`() = runTest {
        whenever(repository.reset(any())).thenReturn(flowOf(true))

        usecase(factory.resetValidParameter).first().run {
            assertNotNull(this)
            assertEquals(this, true)
        }
    }

    @Test
    fun `Should return failure value when trying reset password`() = runTest {
        whenever(repository.reset(any())).thenReturn(flowOf(false))

       usecase(factory.resetValidParameter).first().run {
            assertNotNull(this)
            assertEquals(this, false)
        }
    }
}
