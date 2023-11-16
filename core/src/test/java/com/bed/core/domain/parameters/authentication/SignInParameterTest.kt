package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Assert.assertEquals

import com.bed.core.values.getFirstMessage

internal class SignInParameterTest {

    @Test
    fun `Should try validate SignInParameter return failure when e-mail is invalid`() {
        SignInParameter("emailemail.com", "P@ssw0rD").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "Preencha um e-mail válido.")
        }
    }

    @Test
    fun `Should try validate SignInParameter return failure when password is invalid`() {
        SignInParameter("email@email.com", "P@ss").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "A senha presica conter mais de 6 caracteres.")
        }
    }

    @Test
    fun `Should try validate SignInParameter return failure when e-mail and password is invalid`() {
        SignInParameter("emailemail.com", "P@ss").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "Preencha um e-mail válido.")
        }
    }

    @Test
    fun `Should try validate SignInParameter return success`() {
        SignInParameter("email@email.com", "P@ssw0rD").map { (email, password) ->
            assertEquals(email(), "email@email.com")
            assertEquals(password(), "P@ssw0rD")
        }
    }
}
