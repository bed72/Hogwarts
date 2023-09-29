package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.Email

import com.bed.test.factories.AuthenticationFactory

internal class RecoverParameterTest {
    private lateinit var factory: AuthenticationFactory

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
    }

    @Test
    fun `Should try validate Recover Parameter return success`() {
        assertEquals(mutableSetOf<String>(), factory.recoverValidParameter.hasMessages())
    }

    @Test
    fun `Should try validate Recover Parameter return failure when partial e-mail is invalid`() {
        factory.recoverInvalidParameter
            .copy(Email("emailemail.com"))
            .hasMessages()
            .firstNotNullOf { message -> assertEquals("Preencha um e-mail válido.", message) }
    }

    @Test
    fun `Should try validate Recover Parameter return failure when e-mail is invalid`() {
        factory.recoverInvalidParameter
            .hasMessages()
            .firstNotNullOf { message -> assertEquals("Preencha um e-mail válido.", message) }
    }
}
