package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.Email
import com.bed.core.values.Password

import com.bed.test.factories.AuthenticationFactory

internal class AuthenticationParameterTest {
    private lateinit var factory: AuthenticationFactory

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
    }

    @Test
    fun `Should try validate Authentication Parameter return success`() {
        assertEquals(mutableSetOf<String>(), factory.signInAndSingUpValidParameter.hasMessages())
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail is invalid`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email(""),
                password = Password("P@ssw0rD")
            )
            .hasMessages()
            .firstNotNullOf { message -> assertEquals("O e-mail não pode ser nulo.", message) }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when password is invalid`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("email@email.com"),
                password = Password("")
            )
            .hasMessages()
            .firstNotNullOf { message -> assertEquals("A senha não pode ser nula.", message) }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when password is smaller`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("email@email.com"),
                password = Password("Pa")
            )
            .hasMessages()
            .firstNotNullOf { message ->
                assertEquals("A senha precisa ser maior que 4 caracteres.", message)
            }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when password is bigger`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("email@email.com"),
                password = Password("P@ssw0rDP@ssw0rDP@ssw0rDP@ssw0rDP@ssw0rD")
            )
            .hasMessages()
            .firstNotNullOf { message ->
                assertEquals("A senha precisa ser menor que 16 caracteres.", message)
            }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail and password is invalid by uppercase characters`() {
        val expect = mutableSetOf(
            "Preencha um e-mail válido.",
            "A senha presica conter caracteres maiúsculos, minúsculas e números."
        )

        val response = factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("emailemail.com"),
                password = Password("passw0rd")
            )
            .hasMessages()

        assertEquals(expect, response)
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail and password is invalid by need number`() {
        val expect = mutableSetOf(
            "Preencha um e-mail válido.",
            "A senha presica conter caracteres maiúsculos, minúsculas e números."
        )

        val response = factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("emailemail.com"),
                password = Password("Password")
            )
            .hasMessages()

        assertEquals(expect, response)
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail and password is empty`() {
        val expect = mutableSetOf("O e-mail não pode ser nulo.", "A senha não pode ser nula.")

        val response = factory.signInAndSingUpInvalidParameter.hasMessages()

        assertEquals(expect, response)
    }
}
