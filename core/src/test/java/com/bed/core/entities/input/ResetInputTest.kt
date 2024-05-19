package com.bed.core.entities.input

import org.junit.Test
import org.junit.Assert.assertEquals

import com.bed.core.entities.value.Values

internal class ResetInputTest {
    @Test
    fun `Should try validate ResetParameter return valid`() {
        ResetInput("code", "P@ssw0rD").map { (code, password) ->
            assertEquals("code", code())
            assertEquals("P@ssw0rD", password())
        }
    }

    @Test
    fun `Should try validate ResetParameter return failure when code is invalid`() {
        ResetInput("", "P@ssw0rD").mapLeft { message ->
            assertEquals(Values.INVALID_CODE.message, message.first())
        }
    }

    @Test
    fun `Should try validate ResetParameter return failure when password is empty`() {
        ResetInput("code", "").mapLeft { message ->
            assertEquals(Values.INVALID_PASSWORD.message, message.first())
        }
    }

    @Test
    fun `Should try validate ResetParameter return failure when password is invalid (needs a number)`() {
        ResetInput("code", "password").mapLeft { message ->
            assertEquals(Values.INVALID_PASSWORD.message, message.first())
        }
    }

    @Test
    fun `Should try validate ResetParameter return failure when password is invalid (needs a capital character)`() {
        ResetInput("code", "passw0rd").mapLeft { message ->
            assertEquals(Values.INVALID_PASSWORD.message, message.first())
        }
    }

    @Test
    fun `Should try validate ResetParameter return failure when code and password is invalid`() {
        ResetInput("", "").mapLeft { message ->
            assertEquals(Values.INVALID_CODE.message, message[0])
            assertEquals(Values.INVALID_PASSWORD.message, message[1])
        }
    }
}
