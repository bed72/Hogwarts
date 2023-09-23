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
        factory.signInAndSingUpValidParameter.isValid().map { (email, password) ->
            assertEquals("email@email.com", email.value)
            assertEquals("P@ssw0rD", password.value)
        }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail is invalid`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email(""),
                password = Password("P@ssw0rD")
            )
            .isValid()
            .mapLeft { message -> assertEquals(mutableSetOf("O e-mail não pode ser nulo."), message) }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when password is invalid`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("email@email.com"),
                password = Password("")
            )
            .isValid().mapLeft { message ->
                assertEquals(mutableSetOf("A senha não pode ser nula."), message)
            }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when password is smaller`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("email@email.com"),
                password = Password("Pa")
            )
            .isValid().mapLeft { message ->
                assertEquals(mutableSetOf("A senha precisa ser maior que 4 caracteres."), message)
            }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when password is bigger`() {
        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("email@email.com"),
                password = Password("P@ssw0rDP@ssw0rDP@ssw0rDP@ssw0rDP@ssw0rD")
            )
            .isValid().mapLeft { message ->
                assertEquals(mutableSetOf("A senha precisa ser menor que 16 caracteres."), message)
            }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail and password is invalid by uppercase characters`() {
        val expect = mutableSetOf(
            "Preencha um e-mail válido.",
            "A senha presica conter caracteres maiúsculos, minúsculas e números."
        )

        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("emailemail.com"),
                password = Password("passw0rd")
            )
            .isValid().mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail and password is invalid by need number`() {
        val expect = mutableSetOf(
            "Preencha um e-mail válido.",
            "A senha presica conter caracteres maiúsculos, minúsculas e números."
        )

        factory.signInAndSingUpInvalidParameter
            .copy(
                email = Email("emailemail.com"),
                password = Password("Password")
            )
            .isValid().mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Authentication Parameter return failure when e-mail and password is empty`() {
        val expect = mutableSetOf("O e-mail não pode ser nulo.", "A senha não pode ser nula.")

        factory.signInAndSingUpInvalidParameter
            .isValid().mapLeft { message -> assertEquals(expect, message) }
    }
}
