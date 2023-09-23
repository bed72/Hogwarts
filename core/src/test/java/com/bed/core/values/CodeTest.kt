package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

internal class CodeTest {
    @Test
    fun `Should return message failure when Sting is invalid`() {
        val data = Code("")

        assertFalse(data.isValid)
        assertEquals("O código não é válido.", data.message)
    }

    @Test
    fun `Should return the String when value is invalid`() {
        val data = Code("bed")

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals("bed", data.value)
    }
}
