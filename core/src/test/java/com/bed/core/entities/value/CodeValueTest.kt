package com.bed.core.entities.value

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class CodeValueTest {
    @Test
    fun `Should return the Code when value is valid`() {
        val name = CodeValue("code")

        assertTrue(name.isRight())
        name.map { assertEquals("code", it()) }
    }


    @Test
    fun `Should return message failure when Code is invalid`() {
        val message = CodeValue("")

        assertTrue(message.isLeft())
        message.mapLeft { assertEquals(Values.INVALID_CODE.message, it.first()) }
    }
}
