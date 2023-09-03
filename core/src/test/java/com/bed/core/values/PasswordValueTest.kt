package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class PasswordValueTest {

    @Test
    fun `Should return message failure when Password is invalid`() {
        val message = PasswordValue("")

        val validator = message.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("Preencha uma senha válida.", it) }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations length more 5 character required`() {
        val message = PasswordValue("Pa")

        val validator = message.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A senha presica conter mais de 5 caracteres.", it) }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations numeric character required`() {
        val message = PasswordValue("password")

        val validator = message.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A senha presica conter caracteres numéricos.", it) }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations capital character required`() {
        val message = PasswordValue("passw0rd")

        val validator = message.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A senha presica conter caracteres maiúsculos.", it) }
    }

    @Test
    fun `Should return the Name when value is valid`() {
        val name = PasswordValue("P@ssw0rD")

        val validator = name.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("P@ssw0rD", it.value ) }
    }
}
