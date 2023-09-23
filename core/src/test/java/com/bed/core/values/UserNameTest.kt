package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

internal class UserNameTest {
    @Test
    fun `Should return message failure when User Name is invalid`() {
        val data = UserName("")

        assertFalse(data.isValid)
        assertEquals("Preencha um nome e sobrenome válidos.", data.message)
    }

    @Test
    fun `Should return message failure when User Name is invalid with partial validations`() {
        val data = UserName("Ga Ra")

        assertFalse(data.isValid)
        assertEquals("Preencha um nome e sobrenome válidos.", data.message)
    }

    @Test
    fun `Should return message failure when User Name is invalid with very small size`() {
        val data = UserName("G")

        assertFalse(data.isValid)
        assertEquals("O nome e sobrenome precisam ser maior que 2 caracteres.", data.message)
    }

    @Test
    fun `Should return message failure when User Name is invalid with very large size`() {
        val data = UserName("Gabriel Josoé Silva Ramos Gabriel Josoé Silva Ramos")

        assertFalse(data.isValid)
        assertEquals("O nome e sobrenome precisam ser menor que 32 caracteres.", data.message)
    }

    @Test
    fun `Should return the User Name when value is valid`() {
        val data = UserName("Gabriel Ramos")

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals("Gabriel Ramos", data.value)
    }
}
