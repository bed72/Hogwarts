package com.bed.core.values

import java.time.Month
import java.time.LocalDateTime

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

internal class ValidatedAtTest {

    @Test
    fun `Should return message when Date is invalid`() {
        val data = ValidatedAt(LocalDateTime.now())

        assertFalse(data.isValid)
        assertEquals("A data precisa ser após o dia de hoje.", data.message)
    }

    @Test
    fun `Should return the format Date when value is valid`() {
        val data = ValidatedAt(LocalDateTime.of(2072, Month.JULY, 27, 12, 0))

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals("27/07/2072", data.toDate)
    }
}
