package com.bed.core.entities.input

import org.junit.Test
import org.junit.Assert.assertEquals

import com.bed.core.entities.value.Values

internal class AuthenticationInputTest {
    @Test
    fun `Should try validate SignInParameter return valid`() {
        AuthenticationInput("email@email.com", "P@ssw0rD").map { (email, password) ->
            assertEquals("email@email.com", email())
            assertEquals("P@ssw0rD", password())
        }
    }

    @Test
    fun `Should try validate SignInParameter return failure when e-mail is invalid`() {
        AuthenticationInput("emailemail.com", "P@ssw0rD").mapLeft { message ->
            assertEquals(Values.INVALID_EMAIL.message, message.first())
        }
    }

    @Test
    fun `Should try validate SignInParameter return failure when password is invalid`() {
        AuthenticationInput("email@email.com", "P@ss").mapLeft { message ->
            assertEquals(Values.INVALID_PASSWORD.message, message.first())
        }
    }

    @Test
    fun `Should try validate SignInParameter return failure when e-mail and password is invalid`() {
        AuthenticationInput("emailemail.com", "P@ss").mapLeft { message ->
            assertEquals(Values.INVALID_EMAIL.message, message[0])
            assertEquals(Values.INVALID_PASSWORD.message, message[1])
        }
    }
}
