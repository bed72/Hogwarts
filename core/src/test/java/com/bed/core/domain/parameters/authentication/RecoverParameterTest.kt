package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.EmailValue

import com.bed.test.factories.authentication.AuthenticationFactory

internal class RecoverParameterTest {
    private lateinit var factory: AuthenticationFactory

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
    }

    @Test
    fun `Should try validate Recover Parameter return success`() {
        factory.recoverValidParameter.isValid().map { (email) ->
            assertEquals("email@email.com", email.value)
        }
    }

    @Test
    fun `Should try validate Recover Parameter return failure when partial e-mail is invalid`() {
        factory.recoverInvalidParameter
            .copy(EmailValue("emailemail.com"))
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha um e-mail válido."), message) }
    }

    @Test
    fun `Should try validate Recover Parameter return failure when e-mail is invalid`() {
        factory.recoverInvalidParameter
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha um e-mail válido."), message) }
    }
}
