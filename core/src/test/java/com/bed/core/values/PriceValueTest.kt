package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class PriceValueTest {
    @Test
    fun `Should return message when Price is invalid`() {
        val value = PriceValue(0.0)

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("Preencha um valor maior que R\$ 0.", it) }
    }

    @Test
    fun `Should return the format Date when value is valid`() {
        val value = PriceValue(27.72)

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("R\$ 27,72", it.toPrice()) }
    }
}
