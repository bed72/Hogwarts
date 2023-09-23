package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

internal class EmailTest {
    @Test
    fun `Should return message failure when E-mail is empty`() {
        val data = Email("")

        assertFalse(data.of.isValid)
        assertEquals("O e-mail não pode ser nulo.", data.message)
    }

    @Test
    fun `Should return message failure when E-mail is invalid`() {
        val data = Email("email.com")

        assertFalse(data.isValid)
        assertEquals("Preencha um e-mail válido.", data.message)
    }

    @Test
    fun `Should return the E-mail when value is valid`() {
        val data = Email("email@email.com")

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals("email@email.com", data.value)
    }
}
