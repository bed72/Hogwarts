package com.bed.core.entities.value

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class NameValueTest {
    @Test
    fun `Should return the Name when value is valid`() {
        val name = NameValue("Gabriel Ramos")

        assertTrue(name.isRight())
        name.map { assertEquals("Gabriel Ramos", it()) }
    }

    @Test
    fun `Should return failure message when value is invalid`() {
        listOf("", "Gabriel", "Ramos", "Ga Ra").forEach {
            val name = NameValue(it)

            assertTrue(name.isLeft())
            name.mapLeft { message ->  assertEquals(Values.INVALID_FULL_NAME.message, message.first()) }
        }
    }
}
