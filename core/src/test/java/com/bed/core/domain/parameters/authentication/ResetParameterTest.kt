package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.Code
import com.bed.core.values.Password

import com.bed.test.factories.AuthenticationFactory

internal class ResetParameterTest {
    private lateinit var factory: AuthenticationFactory

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
    }

    @Test
    fun `Should try validate Reset Parameter return success`() {
        assertEquals(mutableSetOf<String>(), factory.resetValidParameter.hasMessages())
    }

    @Test
    fun `Should try validate Reset Parameter return failure when code is empty`() {
        factory.resetInvalidParameter
            .copy(
                code = Code(""),
                password = Password("P@ssw0rD"),
            )
            .hasMessages()
            .firstNotNullOf { message -> assertEquals("O código não é válido.", message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when password is empty`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("")
            )
            .hasMessages()
            .firstNotNullOf { message -> assertEquals("A senha não pode ser nula.", message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial password is invalid ny need number`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("P@ssworD")
            )
            .hasMessages()
            .firstNotNullOf { message ->
                assertEquals("A senha presica conter caracteres maiúsculos, minúsculas e números.", message)
            }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial password is invalid ny need uppercase characters`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("p@ssw0rd")
            )
            .hasMessages()
            .firstNotNullOf { message ->
                assertEquals("A senha presica conter caracteres maiúsculos, minúsculas e números.", message)
            }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when password is smaller`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("p")
            )
            .hasMessages()
            .firstNotNullOf { message ->
                assertEquals("A senha precisa ser maior que 4 caracteres.", message)
            }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial password is bigger`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("P@ssworDP@ssworDP@ssworDP@ssworD")
            )
            .hasMessages()
            .firstNotNullOf { message ->
                assertEquals("A senha precisa ser menor que 16 caracteres.", message)
            }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when code, password is empty`() {
        val expect = mutableSetOf("O código não é válido.", "A senha não pode ser nula.")

        val response = factory.resetInvalidParameter
            .copy(
                code = Code(""),
                password = Password("")
            )
            .hasMessages()

        assertEquals(expect, response)
    }
}
