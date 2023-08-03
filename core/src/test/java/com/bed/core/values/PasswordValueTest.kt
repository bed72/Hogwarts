package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class PasswordValueTest {

    @Test
    fun `Should return message failure when Password is invalid`() {
        val message = PasswordValue("")

        val validator = message()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals(it, "Preencha sua senha.") }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations number character required`() {
        val message = PasswordValue("Pa")

        val validator = message()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals(it, "A senha presica conter caracteres numéricos.") }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations capital character required`() {
        val message = PasswordValue("passw0")

        val validator = message()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals(it, "A senha presica conter caracteres maiúsculos.") }
    }

    @Test
    fun `Should return the Name when value is valid`() {
        val name = PasswordValue("P@ssw0rD")

        val validator = name()

        assertTrue(validator.isRight())
        validator.map { assertEquals(it, "P@ssw0rD") }
    }
}
