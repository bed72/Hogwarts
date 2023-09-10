package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.StringValue
import com.bed.core.values.PasswordValue

import com.bed.test.factories.authentication.AuthenticationFactory

internal class ResetParameterTest {
    private lateinit var factory: AuthenticationFactory

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
    }

    @Test
    fun `Should try validate Reset Parameter return success`() {
        factory.resetValidParameter.isValid().map { (code, password, repeatPassword) ->
            assertEquals("5CQcsREkB5xcqbY1L...", code.value)
            assertEquals("P@ssw0rD", password.value)
            assertEquals("P@ssw0rD", repeatPassword.value)
        }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when code is invalid`() {
        factory.resetInvalidParameter
            .copy(
                code = StringValue(""),
                password = PasswordValue("P@ssw0rD"),
                repeatPassword = PasswordValue("P@ssw0rD")
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha um valor válido."), message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when password is invalid`() {
        val expect = listOf(
            "Preencha uma senha válida.",
            "As senhas precisam ser iguais."
        )

        factory.resetInvalidParameter
            .copy(
                code = StringValue("5CQcsREkB5xcqbY1L.."),
                password = PasswordValue(""),
                repeatPassword = PasswordValue("P@ssw0rD")
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial password is invalid ny need number`() {
        val expect = listOf(
            "A senha presica conter caracteres numéricos.",
            "As senhas precisam ser iguais."
        )

        factory.resetInvalidParameter
            .copy(
                code = StringValue("5CQcsREkB5xcqbY1L.."),
                password = PasswordValue("P@ssworD"),
                repeatPassword = PasswordValue("P@ssw0rD")
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial password is invalid ny need uppercase characters`() {
        val expect = listOf(
            "A senha presica conter caracteres maiúsculos.",
            "As senhas precisam ser iguais."
        )

        factory.resetInvalidParameter
            .copy(
                code = StringValue("5CQcsREkB5xcqbY1L.."),
                password = PasswordValue("p@ssw0rd"),
                repeatPassword = PasswordValue("P@ssw0rD")
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when repeat password is invalid`() {
        val expect = listOf(
            "Preencha uma senha válida.",
            "As senhas precisam ser iguais."
        )

        factory.resetInvalidParameter
            .copy(
                code = StringValue("5CQcsREkB5xcqbY1L.."),
                password = PasswordValue("P@ssw0rD"),
                repeatPassword = PasswordValue("")
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial repeat password is invalid ny need number`() {
        val expect = listOf(
            "A senha presica conter caracteres numéricos.",
            "As senhas precisam ser iguais."
        )

        factory.resetInvalidParameter
            .copy(
                code = StringValue("5CQcsREkB5xcqbY1L.."),
                password = PasswordValue("P@ssw0rD"),
                repeatPassword = PasswordValue("P@ssworD")
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when partial repeat password is invalid ny need uppercase characters`() {
        val expect = listOf(
            "A senha presica conter caracteres maiúsculos.",
            "As senhas precisam ser iguais."
        )

        factory.resetInvalidParameter
            .copy(
                code = StringValue("5CQcsREkB5xcqbY1L.."),
                password = PasswordValue("P@ssw0rD"),
                repeatPassword = PasswordValue("p@ssw0rd")
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Reset Parameter return failure when code, password and repeat password is empty`() {
        val expect = listOf(
            "Preencha um valor válido.",
            "Preencha uma senha válida.",
            "Preencha uma senha válida."
        )

        factory.resetInvalidParameter
            .copy(
                code = StringValue(""),
                password = PasswordValue(""),
                repeatPassword = PasswordValue("")
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }
}
