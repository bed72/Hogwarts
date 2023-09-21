package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class UserNameValueTest {
    @Test
    fun `Should return message failure when User Name is invalid`() {
        val value = UserNameValue("")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("O nome e sobrenome precisam ser maior que 2 caracteres.", it) }
    }

    @Test
    fun `Should return message failure when User Name is invalid with partial validations`() {
        val value = UserNameValue("Ga Ra")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("Preencha um nome e sobrenome válidos.", it) }
    }

    @Test
    fun `Should return message failure when User Name is invalid with very large size`() {
        val value = UserNameValue("Gabriel Josoé Silva Ramos Gabriel Josoé Silva Ramos")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("O nome e sobrenome precisam ser menor que 32 caracteres.", it) }
    }

    @Test
    fun `Should return the User Name when value is valid`() {
        val value = UserNameValue("Gabriel Ramos")

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("Gabriel Ramos", it.value) }
    }
}
