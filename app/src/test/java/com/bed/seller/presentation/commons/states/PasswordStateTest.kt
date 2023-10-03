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

internal class PasswordStateTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<PasswordState.States>

    private  lateinit var password: PasswordState

    @Before
    fun setUp() {
        password = PasswordState(rule.dispatcher).apply { states.observeForever(observer) }
    }

    @Test
    fun `Should emit Success State when trying validate valid Password`() = runTest {
        password.set("P@ssw0rd")

        verify(observer).onChanged(isA<PasswordState.States.Success>())
    }

    @Test
    fun `Should emit Failure State when trying validate invalid Password`() = runTest {
        password.set("password")

        verify(observer).onChanged(isA<PasswordState.States.Failure>())
    }

    @Test
    fun `Should show message when valid Password`() = runTest {
        password.set("Passw0rd")

        val (success) = password.states.value as PasswordState.States.Success

        assertEquals("Passw0rd", success.value)
    }

    @Test
    fun `Should show message when empty Password`() = runTest {
        password.set("")

        val (failure) = password.states.value as PasswordState.States.Failure

        assertEquals("Preencha uma senha válida.", failure)
    }

    @Test
    fun `Should show message when no capital letters in Password`() = runTest {
        password.set("passw0rd")

        val (failure) = password.states.value as PasswordState.States.Failure

        assertEquals("A senha presica conter caracteres maiúsculos, minúsculas e números.", failure)
    }

    @Test
    fun `Should show message when no numbers in Password`() = runTest {
        password.set("Password")

        val (failure) = password.states.value as PasswordState.States.Failure

        assertEquals("A senha presica conter caracteres maiúsculos, minúsculas e números.", failure)
    }

    @Test
    fun `Should show message when Password is small`() = runTest {
        password.set("Pas")

        val (failure) = password.states.value as PasswordState.States.Failure

        assertEquals("A senha precisa ser maior que 4 caracteres.", failure)
    }

    @Test
    fun `Should show message when Password is big`() = runTest {
        password.set("Passw0rdPassw0rdPassw0rdPassw0rdPassw0rdPassw0rd")

        val (failure) = password.states.value as PasswordState.States.Failure

        assertEquals("A senha precisa ser menor que 16 caracteres.", failure)
    }
}
