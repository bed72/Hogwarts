package com.bed.core.entities.input

import org.junit.Test
import org.junit.Assert.assertEquals

import com.bed.core.entities.value.Values

internal class RecoverInputTest {
    @Test
    fun `Should try validate RecoverParameter return valid`() {
        RecoverInput("email@email.com").map { (email) ->
            assertEquals("email@email.com", email())
        }
    }

    @Test
    fun `Should try validate RecoverParameter return failure when e-mail is invalid`() {
        RecoverInput("emailemail.com").mapLeft { message ->
            assertEquals(Values.INVALID_EMAIL.message, message.first())
        }
    }
}
