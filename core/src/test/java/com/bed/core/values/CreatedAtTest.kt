package com.bed.core.values

import java.time.Month
import java.time.LocalDateTime

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

internal class CreatedAtTest {
    @Test
    fun `Should return message when Date is invalid`() {
        val data = CreatedAt(LocalDateTime.of(2023, Month.JULY, 27, 12, 0))

        assertFalse(data.isValid)
        assertEquals("A data não corresponde ao dia de hoje.", data.message)
    }

    @Test
    fun `Should return the format Date when value is valid`() {
        val data = CreatedAt(LocalDateTime.now())

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
    }
}
