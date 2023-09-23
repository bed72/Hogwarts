package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

internal class DescriptionTest {
    @Test
    fun `Should return message failure when Description is empty`() {
        val data = Description("")

        assertFalse(data.of.isValid)
        assertEquals("A descrição não pode ser nula.", data.message)
    }

    @Test
    fun `Should return message failure when Description is short`() {
        val data = Description("Lor")

        assertFalse(data.of.isValid)
        assertEquals("A descrição precisa ser maior que 4 caracteres.", data.message)
    }

    @Test
    fun `Should return message failure when Description is especial characters`() {
        val data = Description("@@%$&")

        assertFalse(data.of.isValid)
        assertEquals("Preencha uma descrição válida.", data.message)
    }

    @Test
    fun `Should return message failure when Description is invalid with very large size`() {
        val data = Description("""
            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum
            has been the industry's standard dummy text ever since the 1500s, when an unknown printer
            took a galley of type and scrambled it to make a type specimen book. It has survived not
            only five centuries, but also the leap into electronic typesetting, remaining essentially
            unchanged. It was popularised in the 1960s with the release of Letraset sheets containing
            Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker
            including versions of Lorem Ipsum.
        """)

        assertFalse(data.of.isValid)
        assertEquals("A descrição precisa ser menor que 64 caracteres.", data.message)
    }

    @Test
    fun `Should return the Description when value is valid`() {
        val data = Description("Lorem")

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals("Lorem", data.value)
    }

    @Test
    fun `Should return the Description when value is valid with space`() {
        val data = Description("The better coffee, this city!")

        assertTrue(data.isValid)
        assertEquals(0, data.of.size)
        assertEquals("The better coffee, this city!", data.value)
    }
}
