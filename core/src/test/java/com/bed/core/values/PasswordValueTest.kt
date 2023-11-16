package com.bed.core.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class PasswordValueTest {

    @Test
    fun `Should return message failure when Password is invalid`() {
        val message = PasswordValue("")

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "A senha presica conter mais de 6 caracteres.") }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations length more 5 character required`() {
        val message = PasswordValue("Pa")

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "A senha presica conter mais de 6 caracteres.") }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations numeric character required`() {
        val message = PasswordValue("password")

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "A senha presica conter caracteres numéricos.") }
    }

    @Test
    fun `Should return message failure when Password is invalid with partial validations capital character required`() {
        val message = PasswordValue("passw0rd")

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(it.getFirstMessage(), "A senha presica conter caracteres maiúsculos.") }
    }

    @Test
    fun `Should return the Name when value is valid`() {
        val password = PasswordValue("P@ssw0rD")

        assertTrue(password.isRight())
        password.map { assertEquals(it(), "P@ssw0rD") }
    }
}
