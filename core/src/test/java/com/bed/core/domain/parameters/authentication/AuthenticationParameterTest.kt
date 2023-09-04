package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.EmailValue
import com.bed.core.values.PasswordValue

import com.bed.test.factories.authentication.AuthenticationFactory

internal class AuthenticationParameterTest {
    private lateinit var factory: AuthenticationFactory

    @Before
    fun setUp() {
        factory = AuthenticationFactory()
    }

    @Test
    fun `Should try validate Authentication Parameter return success`() {
        factory.signInAndSingUpValidParameter.isValid().map { (email, password) ->
            assertEquals("email@email.com", email.value)
            assertEquals("P@ssw0rD", password.value)
        }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail is invalid`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = EmailValue(""),
                password = PasswordValue("P@ssw0rD"),
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha um e-mail válido."), message) }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when password is invalid`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = EmailValue("email@email.com"),
                password = PasswordValue(""),
            )
            .isValid().mapLeft { message -> assertEquals(listOf("Preencha uma senha válida."), message) }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail and password is invalid`() {
        val expect = listOf(
            "Preencha um e-mail válido.",
            "A senha presica conter caracteres numéricos.",
        )

        factory.signInAndSingUpInvalidParameter
            .copy(
                email = EmailValue("emailemail.com"),
                password = PasswordValue("Password"),
            )
            .isValid().mapLeft { message -> assertEquals(expect, message) }
    }
}
