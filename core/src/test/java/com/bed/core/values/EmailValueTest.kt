package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class EmailValueTest {
    @Test
    fun `Should return message failure when E-mail is invalid`() {
        val value = EmailValue("")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("Preencha um e-mail válido.", it) }
    }

    @Test
    fun `Should return message failure when E-mail is invalid with partial validations`() {
        val value = EmailValue("email.com")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("Preencha um e-mail válido.", it) }
    }

    @Test
    fun `Should return the E-mail when value is valid`() {
        val value = EmailValue("email@email.com")

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("email@email.com", it.value) }
    }
}
