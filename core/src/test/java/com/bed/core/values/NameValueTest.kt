package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class NameValueTest {
    @Test
    fun `Should return the Name when value is valid`() {
        val name = NameValue("Gabriel Ramos")

        assertTrue(name.isRight())
        name.map { assertEquals(it(), "Gabriel Ramos") }
    }

    @Test
    fun `Should return message failure when Name is null`() {
        val message = NameValue(null)

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "O nome e sobrenome não podem ser nulos.") }
    }

    @Test
    fun `Should return message failure when Name is invalid`() {
        val message = NameValue("")

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "Preencha um nome e sobrenome válidos.") }
    }

    @Test
    fun `Should return message failure when Name is invalid with partial validations`() {
        val message = NameValue("Ga Ra")

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "Preencha um nome e sobrenome válidos.") }
    }
}
