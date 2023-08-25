package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.NameValue
import com.bed.core.values.EmailValue
import com.bed.core.values.PasswordValue

import com.bed.test.factories.authentication.SignUpFactory

internal class SignUpParameterTest {
    private lateinit var factory: SignUpFactory

    @Before
    fun setUp() {
        factory = SignUpFactory()
    }

    @Test
    fun `Should try validate SignUpParameter return success`() {
        factory.signUpParameter.isValid().map { (name, email, password) ->
            assertEquals(name.value, "Gabriel Ramos")
            assertEquals(email.value, "email@email.com")
            assertEquals(password.value, "P@ssw0rD")
        }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when name is invalid`() {
        factory.invalidParameter
            .copy(
                name = NameValue(""),
                email = EmailValue("email@email.com"),
                password = PasswordValue("P@ssw0rD"),
            )
            .isValid()
            .mapLeft { message -> assertEquals(message, listOf("Preencha um nome e sobrenome válidos.")) }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when e-mail is invalid`() {
        factory.invalidParameter
            .copy(
                name = NameValue("Gabriel Ramos"),
                email = EmailValue(""),
                password = PasswordValue("P@ssw0rD"),
            )
            .isValid()
            .mapLeft { message -> assertEquals(message, listOf("Preencha um e-mail válido.")) }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when password is invalid`() {
        factory.invalidParameter
            .copy(
                name = NameValue("Gabriel Ramos"),
                email = EmailValue("email@email.com"),
                password = PasswordValue(""),
            )
            .isValid().mapLeft { message -> assertEquals(message, listOf("Preencha uma senha válida.")) }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when name and password is invalid`() {
        val expect = listOf(
            "Preencha um nome e sobrenome válidos.",
            "A senha presica conter caracteres numéricos.",
        )

        factory.invalidParameter
            .copy(
                name = NameValue("Ga"),
                email = EmailValue("email@email.com"),
                password = PasswordValue("P@sswrD"),
            )
            .isValid().mapLeft { message -> assertEquals(message, expect) }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when name and e-mail is invalid`() {
        val expect = listOf(
            "Preencha um nome e sobrenome válidos.",
            "Preencha um e-mail válido.",
        )

        factory.invalidParameter
            .copy(
                name = NameValue("Ga"),
                email = EmailValue("emailemail.com"),
                password = PasswordValue("P@ssw0rD"),
            )
            .isValid().mapLeft { message -> assertEquals(message, expect) }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when e-mail and password is invalid`() {
        val expect = listOf(
            "Preencha um e-mail válido.",
            "A senha presica conter caracteres numéricos.",
        )

        factory.invalidParameter
            .copy(
                name = NameValue("Gabriel Ramos"),
                email = EmailValue("emailemail.com"),
                password = PasswordValue("Password"),
            )
            .isValid().mapLeft { message -> assertEquals(message, expect) }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when name, e-mail and password is invalid`() {
        val expect = listOf(
            "Preencha um nome e sobrenome válidos.",
            "Preencha um e-mail válido.",
            "A senha presica conter caracteres maiúsculos.",
        )

        factory.invalidParameter
            .copy(
                name = NameValue("Ga"),
                email = EmailValue("emailemail.com"),
                password = PasswordValue("p@sswr0d"),
            )
            .isValid().mapLeft { message -> assertEquals(message, expect) }
    }
}
