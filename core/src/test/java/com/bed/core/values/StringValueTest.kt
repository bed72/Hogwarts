package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class StringValueTest {
    @Test
    fun `Should return message failure when Sting is invalid`() {
        val message = StringValue("")

        val validator = message.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("Preencha um valor válido.", it) }
    }

    @Test
    fun `Should return the String when value is invalid`() {
        val message = StringValue("bed")

        val validator = message.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("bed", it.value) }
    }
}
