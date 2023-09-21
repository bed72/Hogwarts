package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class ProductNameValueTest {
    @Test
    fun `Should return message failure when Product Name is invalid`() {
        val value = ProductNameValue("")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("O nome do produto precisa ser maior que 2 caracteres.", it) }
    }

    @Test
    fun `Should return message failure when Product Name is invalid with partial validations`() {
        val value = ProductNameValue("Co")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("O nome do produto precisa ser maior que 2 caracteres.", it) }
    }

    @Test
    fun `Should return message failure when Product Name is invalid with very large size`() {
        val value = ProductNameValue("Coffee Coffee Coffee Coffee Coffee Coffee")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("O nome do produto precisa ser menor que 16 caracteres.", it) }
    }

    @Test
    fun `Should return the Product Name when value is valid`() {
        val value = ProductNameValue("Coffee")

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("Coffee", it.value) }
    }

    @Test
    fun `Should return the Product Name when value is valid with space`() {
        val value = ProductNameValue("Dark Coffee")

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("Dark Coffee", it.value) }
    }
}
