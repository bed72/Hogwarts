package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.EmailValue
import com.bed.core.values.PasswordValue

import com.bed.test.factories.authentication.SignInFactory

internal class SignInParameterTest {
    private lateinit var factory: SignInFactory

    @Before
    fun setUp() {
        factory = SignInFactory()
    }

    @Test
    fun `Should try validate SignInParameter return success`() {
        factory.signInParameter.isValid().map { (email, password) ->
            assertEquals(email.value, "email@email.com")
            assertEquals(password.value, "P@ssw0rD")
        }
    }

    @Test
    fun `Should try validate SignInParameter return failure when e-mail is invalid`() {
        factory.invalidParameter
            .copy(
                email = EmailValue(""),
                password = PasswordValue("P@ssw0rD"),
            )
            .isValid()
            .mapLeft { message -> assertEquals(message, listOf("Preencha um e-mail válido.")) }
    }

    @Test
    fun `Should try validate SignInParameter return failure when password is invalid`() {
        factory.invalidParameter
            .copy(
                email = EmailValue("email@email.com"),
                password = PasswordValue(""),
            )
            .isValid().mapLeft { message -> assertEquals(message, listOf("Preencha uma senha válida.")) }
    }

    @Test
    fun `Should try validate SignInParameter return failure when e-mail and password is invalid`() {
        val expect = listOf(
            "Preencha um e-mail válido.",
            "A senha presica conter caracteres numéricos.",
        )

        factory.invalidParameter
            .copy(
                email = EmailValue("emailemail.com"),
                password = PasswordValue("Password"),
            )
            .isValid().mapLeft { message -> assertEquals(message, expect) }
    }
}
