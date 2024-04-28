package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class EmailValueTest {
    @Test
    fun `Should return the E-mail when value is valid`() {
        val email = EmailValue("email@email.com")

        assertTrue(email.isRight())
        email.map { assertEquals(it(), "email@email.com") }
    }

    @Test
    fun `Should return message failure when E-mail is empty`() {
        val message = EmailValue(null)

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "O e-mail não pode ser nulo.") }
    }

    @Test
    fun `Should return message failure when E-mail is invalid`() {
        val message = EmailValue("")

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "Preencha um e-mail válido.") }
    }

    @Test
    fun `Should return message failure when E-mail is invalid with partial validations`() {
        val message = EmailValue("email.com")

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "Preencha um e-mail válido.") }
    }
}
