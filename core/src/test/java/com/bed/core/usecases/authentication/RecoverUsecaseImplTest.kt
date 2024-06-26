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
internal class RecoverUsecaseImplTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var usecase: RecoverUsecase
    private lateinit var factory: AuthenticationFactory

    @Mock
    private lateinit var repository: AuthenticationRepository

    @Before
    fun setup() {
        factory = AuthenticationFactory()
        usecase = RecoverUsecaseImpl(rule.dispatcher, repository)
    }

    @Test
    fun `Should return value not null when trying verify is recover account with successful return`() = runTest {
        whenever(repository.recover(any())).thenReturn(flowOf(true))

        val response = usecase(factory.recoverValidParameter).first()

        assertNotNull(response)
    }

    @Test
    fun `Should only call repository once when trying verify is recover account`() = runTest {
        whenever(repository.recover(any())).thenReturn(flowOf(false))

        usecase(factory.recoverValidParameter).first()

        verify(repository, times(1)).recover(any())
    }

    @Test
    fun `Should return success value when trying verify is recover account`() = runTest {
        whenever(repository.recover(any())).thenReturn(flowOf(true))

        val response = usecase(factory.recoverValidParameter).first()

        assertEquals(true, response)
    }

    @Test
    fun `Should return value not null when trying verify is recover account with failure return`() = runTest {
        whenever(repository.recover(any())).thenReturn(flowOf(false))

        val response = usecase(factory.recoverValidParameter).first()

        assertNotNull(response)
    }

    @Test
    fun `Should return failure value when trying verify is recover account`() = runTest {
        whenever(repository.recover(any())).thenReturn(flowOf(false))

        val response = usecase(factory.recoverValidParameter).first()

        assertEquals(false, response)
    }
}
