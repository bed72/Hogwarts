package com.bed.seller.presentation.commons.states

import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith

import junit.framework.TestCase.assertEquals

import org.mockito.Mock
import org.mockito.kotlin.isA
import org.mockito.kotlin.verify
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.lifecycle.Observer
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bed.test.rule.MainCoroutineRule

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class EmailStateTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<EmailState.States>

    private lateinit var email: EmailState

    @Before
    fun setUp() {
        email = EmailState(rule.dispatcher).apply { states.observeForever(observer) }
    }

    @Test
    fun `Should emit Success State when trying validate valid Email`() = runTest {
        email.set("email@email.com")

        verify(observer).onChanged(isA<EmailState.States.Success>())
    }

    @Test
    fun `Should emit Failure State when trying validate invalid Email`() = runTest {
        email.set("emailemail.com")

        verify(observer).onChanged(isA<EmailState.States.Failure>())
    }

    @Test
    fun `Should show message when valid Email`() = runTest {
        email.set("email@email.com")

        val (success) = email.states.value as EmailState.States.Success

        assertEquals("email@email.com", success.value)
    }

    @Test
    fun `Should show message when invalid Email`() = runTest {
        email.set("emailemail.com")

        val (failure) = email.states.value as EmailState.States.Failure

        assertEquals("Preencha um e-mail válido.", failure)
    }
}
