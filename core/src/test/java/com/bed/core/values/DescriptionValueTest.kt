package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class DescriptionValueTest {
    @Test
    fun `Should return message failure when Description is invalid`() {
        val value = DescriptionValue("")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A descrição precisam ser maior que 2 caracteres.", it) }
    }

    @Test
    fun `Should return message failure when Description is invalid with partial validations`() {
        val value = DescriptionValue("Co")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A descrição precisam ser maior que 2 caracteres.", it) }
    }

    @Test
    fun `Should return message failure when Description is invalid with very large size`() {
        val value = DescriptionValue("""
            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum 
            has been the industry's standard dummy text ever since the 1500s, when an unknown printer
            took a galley of type and scrambled it to make a type specimen book. It has survived not
            only five centuries, but also the leap into electronic typesetting, remaining essentially
            unchanged. It was popularised in the 1960s with the release of Letraset sheets containing
            Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker
            including versions of Lorem Ipsum.
        """.trimIndent())

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A descrição precisam ser menor que 64 caracteres.", it) }
    }

    @Test
    fun `Should return the Description when value is valid`() {
        val value = DescriptionValue("Coffee")

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("Coffee", it.value) }
    }

    @Test
    fun `Should return the Description when value is valid with space`() {
        val value = DescriptionValue("The better coffee, this city!")

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("The better coffee, this city!", it.value) }
    }
}
