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
        factory.resetValidParameter.isValid().map { (code, password) ->
            assertEquals("5CQcsREkB5xcqbY1L...", code.value)
            assertEquals("P@ssw0rD", password.value)
        }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when code is empty`() {
        factory.resetInvalidParameter
            .copy(
                code = Code(""),
                password = Password("P@ssw0rD"),
            )
            .isValid()
            .mapLeft { message -> assertEquals(mutableSetOf("O código não é válido."), message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when password is empty`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("")
            )
            .isValid()
            .mapLeft { message -> assertEquals(mutableSetOf("A senha não pode ser nula."), message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial password is invalid ny need number`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("P@ssworD")
            )
            .isValid()
            .mapLeft { message ->
                assertEquals(mutableSetOf("A senha presica conter caracteres maiúsculos, minúsculas e números."), message)
            }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial password is invalid ny need uppercase characters`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("p@ssw0rD")
            )
            .isValid()
            .mapLeft { message ->
                assertEquals(mutableSetOf("A senha presica conter caracteres maiúsculos, minúsculas e números."), message)
            }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when password is smaller`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("p")
            )
            .isValid()
            .mapLeft { message ->
                assertEquals(mutableSetOf("A senha precisa ser maior que 4 caracteres."), message)
            }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial password is bigger`() {
        factory.resetInvalidParameter
            .copy(
                code = Code("5CQcsREkB5xcqbY1L.."),
                password = Password("P@ssworDP@ssworDP@ssworDP@ssworD")
            )
            .isValid()
            .mapLeft { message ->
                assertEquals(mutableSetOf("A senha precisa ser menor que 16 caracteres."), message)
            }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when code, password is empty`() {
        val expect = mutableSetOf("O código não é válido.", "A senha não pode ser nula.")

        factory.resetInvalidParameter
            .copy(
                code = Code(""),
                password = Password("")
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }
}
