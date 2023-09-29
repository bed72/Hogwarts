package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

internal class PasswordTest {
    @Test
    fun `Should return message failure when Password is invalid`() {
        val data = Password("")

        assertFalse(data.of.isValid)
        assertEquals("Preencha uma senha válida.", data.message)
    }

    @Test
    fun `Should return message failure when Password is smaller`() {
        val data = Password("Pa")

        assertFalse(data.of.isValid)
        assertEquals("A senha precisa ser maior que 4 caracteres.", data.message)
    }

    @Test
    fun `Should return message failure when Password is bigger`() {
        val data = Password("Passw0rdPassw0rdPassw0rdPassw0rdPassw0rd")

        assertFalse(data.of.isValid)
        assertEquals("A senha precisa ser menor que 16 caracteres.", data.message)
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations numeric character required`() {
         val data = Password("Password")

        assertFalse(data.of.isValid)
        assertEquals("A senha presica conter caracteres maiúsculos, minúsculas e números.", data.message)
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations capital character required`() {
        val data = Password("passw0rd")

        assertFalse(data.isValid)
        assertEquals("A senha presica conter caracteres maiúsculos, minúsculas e números.", data.message)
    }

    @Test
    fun `Should return the Name when value is valid`() {
        val data = Password("P@ssw0rD")

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals("P@ssw0rD", data.value)
    }
}
