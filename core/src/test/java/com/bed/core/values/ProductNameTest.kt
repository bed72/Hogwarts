package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

internal class ProductNameTest {
    @Test
    fun `Should return message failure when Product Name is invalid`() {
        val data = ProductName("")

        assertFalse(data.isValid)
        assertEquals("Preencha um nome de produto válido.", data.message)
    }

    @Test
    fun `Should return message failure when Product Name is invalid with partial validations`() {
        val data = ProductName("C")

        assertFalse(data.isValid)
        assertEquals("O nome do produto precisa ser maior que 2 caracteres.", data.message)
    }

    @Test
    fun `Should return message failure when Product Name is invalid with very large size`() {
        val data = ProductName("Coffee Coffee Coffee Coffee Coffee Coffee")

        assertFalse(data.isValid)
        assertEquals("O nome do produto precisa ser menor que 16 caracteres.", data.message)
    }

    @Test
    fun `Should return the Product Name when value is valid`() {
        val data = ProductName("Coffee")

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals("Coffee", data.value)
    }

    @Test
    fun `Should return the Product Name when value is valid with space`() {
        val data = ProductName("Dark Coffee")

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals("Dark Coffee", data.value)
    }
}
