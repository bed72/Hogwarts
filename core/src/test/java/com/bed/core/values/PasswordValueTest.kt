package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class PasswordValueTest {
    @Test
    fun `Should return message failure when Password is invalid`() {
        val value = PasswordValue("")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("Preencha uma senha válida.", it) }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations length more 5 character required`() {
        val value = PasswordValue("Pa")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A senha presica conter mais de 5 caracteres.", it) }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations numeric character required`() {
        val value = PasswordValue("password")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A senha presica conter caracteres numéricos.", it) }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations capital character required`() {
        val value = PasswordValue("passw0rd")

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A senha presica conter caracteres maiúsculos.", it) }
    }

    @Test
    fun `Should return the Name when value is valid`() {
        val value = PasswordValue("P@ssw0rD")

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("P@ssw0rD", it.value ) }
    }
}
