package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Assert.assertEquals

import com.bed.core.values.getFirstMessage

internal class SignUpParameterTest {

    @Test
    fun `Should try validate SignUpParameter return failure when name is invalid`() {
        SignUpParameter("Ga Ra", "email@email.com", "P@ssw0rD").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "Preencha um nome e sobrenome válidos.")
        }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when e-mail is invalid`() {
        SignUpParameter("Gabriel Ramos", "emailemail.com", "P@ssw0rD").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "Preencha um e-mail válido.")
        }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when password is invalid`() {
        SignUpParameter("Gabriel Ramos", "email@email.com", "P@ss").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "A senha presica conter mais de 6 caracteres.")
        }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when name and password is invalid`() {
        SignUpParameter("Ga Ra", "email@email.com", "P@ss").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "Preencha um nome e sobrenome válidos.")
        }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when name and e-mail is invalid`() {
        SignUpParameter("Ga Ra", "emailemail.com", "P@ssW0rD").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "Preencha um nome e sobrenome válidos.")
        }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when e-mail and password is invalid`() {
        SignUpParameter("Gabriel Ramos", "emailemail.com", "P@ss").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "Preencha um e-mail válido.")
        }
    }

    @Test
    fun `Should try validate SignUpParameter return failure when name, e-mail and password is invalid`() {
        SignUpParameter("Ga Ra", "emailemail.com", "P@ss").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "Preencha um nome e sobrenome válidos.")
        }
    }

    @Test
    fun `Should try validate SignUpParameter return success`() {
        SignUpParameter("Gabriel Ramos", "email@email.com", "P@ssw0rD").map { (name, email, password) ->
            assertEquals(name(), "Gabriel Ramos")
            assertEquals(email(), "email@email.com")
            assertEquals(password(), "P@ssw0rD")
        }
    }
}
