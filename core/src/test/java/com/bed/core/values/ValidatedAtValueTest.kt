package com.bed.core.values

import java.time.Month
import java.time.LocalDateTime

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

import com.bed.test.factories.OfferFactory

internal class ValidatedAtValueTest {
    private lateinit var factory: OfferFactory

    @Before
    fun setUp() {
        factory = OfferFactory()
    }

    @Test
    fun `Should return message when Date is invalid`() {
        val value = ValidatedAtValue(LocalDateTime.of(2023, Month.JULY, 27, 12, 0))

        val validator = value.validate()

        assertTrue(validator.isLeft())
        validator.mapLeft { assertEquals("A data precisa ser a partir de amanhã.", it) }
    }

    @Test
    fun `Should return the format Date when value is valid`() {
        val value = ValidatedAtValue(factory.validateAt)

        val validator = value.validate()

        assertTrue(validator.isRight())
        validator.map { assertEquals("27/07/2072", it.toDate()) }
    }
}
