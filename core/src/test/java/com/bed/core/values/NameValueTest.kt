package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class NameValueTest {

    @Test
    fun `Should return message failure when Name is invalid`() {
        val message = NameValue("")

        val validator = message.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals(it, "Preencha seu nome e sobrenome.") }
    }

    @Test
    fun `Should return message failure when Name is invalid with partial validations`() {
        val message = NameValue("Ga Ra")

        val validator = message.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals(it, "O nome e o sobrenome precisam ser válidos.") }
    }

    @Test
    fun `Should return the Name when value is valid`() {
        val name = NameValue("Gabriel Ramos")

        val validator = name.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals(it, "Gabriel Ramos") }
    }
}
