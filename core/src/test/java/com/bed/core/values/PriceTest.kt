package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

internal class PriceTest {
    @Test
    fun `Should return message when Price is zero`() {
        val data = Price(0.0)

        assertFalse(data.isValid)
        assertEquals("Preencha um valor maior que R\$ 0,0.", data.message)
    }

    @Test
    fun `Should return the format Date when value is valid`() {
        val data = Price(27.72)

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals(27.72, data.value, 0.0)
    }
}
