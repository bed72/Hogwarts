package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class CodeValueTest {
    @Test
    fun `Should return message failure when Sting is invalid`() {
        val value = CodeValue("")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("O código não é válido.", it) }
    }

    @Test
    fun `Should return the String when value is invalid`() {
        val value = CodeValue("bed")

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("bed", it.value) }
    }
}
