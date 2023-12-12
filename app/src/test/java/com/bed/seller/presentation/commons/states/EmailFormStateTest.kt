package com.bed.seller.presentation.commons.states

import org.junit.Test
import org.junit.Rule
import org.junit.Before

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import com.bed.test.rule.MainCoroutineRule

import com.bed.test.factories.authentication.AuthenticationFactory

@OptIn(ExperimentalCoroutinesApi::class)
internal class EmailFormStateTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private lateinit var form: FormState

    private lateinit var states: MutableList<States<String>>

    @Before
    fun setUp() {
        form = FormState()
        states = mutableListOf()
    }

    @Test
    fun `Should return successful`() = runTest {
        val job = launch(rule.dispatcher.main()) { form.state.toList(states) }

        form.set("email@email.com", FormState.Type.Email)

        assertEquals(states[AuthenticationFactory.INITIAL], States.Initial)
        assertEquals(states[AuthenticationFactory.LOADING], States.Loading)
        assertTrue(states[AuthenticationFactory.SUCCESS] is States.Success)
        form.state.value.let {
            it as States.Success
            assertEquals(it.data, "email@email.com")
        }

        job.cancel()
    }

    @Test
    fun `Should return failure (empty)`() = runTest {
        val job = launch(rule.dispatcher.main()) { form.state.toList(states) }

        form.set("", FormState.Type.Email)

        assertEquals(states[AuthenticationFactory.INITIAL], States.Initial)
        assertEquals(states[AuthenticationFactory.LOADING], States.Loading)
        assertTrue(states[AuthenticationFactory.FAILURE] is States.Failure)
        form.state.value.let {
            it as States.Failure
            assertEquals(it.data, "Preencha um e-mail válido.")
        }

        job.cancel()
    }

    @Test
    fun `Should return failure (invalid)`() = runTest {
        val job = launch(rule.dispatcher.main()) { form.state.toList(states) }

        form.set("email@email.c", FormState.Type.Email)

        assertEquals(states[AuthenticationFactory.INITIAL], States.Initial)
        assertEquals(states[AuthenticationFactory.LOADING], States.Loading)
        assertTrue(states[AuthenticationFactory.FAILURE] is States.Failure)
        form.state.value.let {
            it as States.Failure
            assertEquals(it.data, "Preencha um e-mail válido.")
        }

        job.cancel()
    }
}
