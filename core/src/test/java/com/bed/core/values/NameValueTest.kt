package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class NameValueTest {
    @Test
    fun `Should return message failure when Name is invalid`() {
        val value = NameValue("")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("Preencha um nome e sobrenome válidos.", it) }
    }

    @Test
    fun `Should return message failure when Name is invalid with partial validations`() {
        val value = NameValue("Ga Ra")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("Preencha um nome e sobrenome válidos.", it) }
    }

    @Test
    fun `Should return the Name when value is valid`() {
        val value = NameValue("Gabriel Ramos")

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("Gabriel Ramos", it.value) }
    }
}
