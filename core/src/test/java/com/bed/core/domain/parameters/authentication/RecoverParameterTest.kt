package com.bed.core.domain.parameters.authentication

import org.junit.Test
import org.junit.Assert.assertEquals

import com.bed.core.values.getFirstMessage

internal class RecoverParameterTest {
    @Test
    fun `Should try validate RecoverParameter return failure when e-mail is invalid`() {
        RecoverParameter("emailemail.com").mapLeft { message ->
            assertEquals(message.getFirstMessage(), "Preencha um e-mail válido.")
        }
    }

    @Test
    fun `Should try validate RecoverParameter return success`() {
        RecoverParameter("email@email.com").map { (email) ->
            assertEquals(email(), "email@email.com")
        }
    }
}
