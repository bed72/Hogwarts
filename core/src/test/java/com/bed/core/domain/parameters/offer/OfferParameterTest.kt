package com.bed.core.domain.parameters.offer

import java.time.Month
import java.time.LocalDateTime

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.DateValue
import com.bed.core.values.PriceValue
import com.bed.core.values.StringValue

import com.bed.test.factories.OfferFactory

internal class OfferParameterTest {
    private lateinit var factory: OfferFactory

    @Before
    fun setUp() {
        factory = OfferFactory()
    }

    @Test
    fun `Should try validate Offer Parameter return success`() {
        factory.offerValidParameter.isValid().map { data ->
            assertEquals("Coffee", data.name.value)
            assertEquals(27.72, data.price.value, 0.0)
            assertEquals("The better coffee this city.", data.description.value)
            assertEquals(factory.createAt, data.createdAt.value)
            assertEquals(factory.validateAt, data.validatedAt.value)
        }
    }

    @Test
    fun `Should try validate Offer Parameter return failure all parameter invalid`() {
        val invalidDate = LocalDateTime.of(2023, Month.SEPTEMBER, 19, 12, 0)
        val expect = listOf(
            "Preencha um valor válido.",
            "Preencha um valor maior que 0.",
            "Preencha um valor válido.",
            "Preencha uma data válida.",
            "Preencha uma data válida."
        )

        factory.offerValidParameter
            .copy(
                name = StringValue(""),
                price = PriceValue(0.0),
                description = StringValue(""),
                createdAt = DateValue(invalidDate),
                validatedAt =  DateValue(invalidDate)
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure name is invalid`() {
        factory.offerValidParameter
            .copy(
                name = StringValue(""),
                price = PriceValue(27.72),
                description = StringValue("The better coffee this city."),
                createdAt = DateValue(factory.createAt),
                validatedAt =  DateValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha um valor válido."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure price is invalid`() {
        factory.offerValidParameter
            .copy(
                name = StringValue("Coffee"),
                price = PriceValue(0.0),
                description = StringValue("The better coffee this city."),
                createdAt = DateValue(factory.createAt),
                validatedAt =  DateValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha um valor maior que 0."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure description is invalid`() {
        factory.offerValidParameter
            .copy(
                name = StringValue("Coffee"),
                price = PriceValue(27.72),
                description = StringValue(""),
                createdAt = DateValue(factory.createAt),
                validatedAt =  DateValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha um valor válido."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure createdAt is invalid`() {
        val invalidDate = LocalDateTime.of(2023, Month.SEPTEMBER, 19, 12, 0)

        factory.offerValidParameter
            .copy(
                name = StringValue("Coffee"),
                price = PriceValue(27.72),
                description = StringValue("The better coffee this city."),
                createdAt = DateValue(invalidDate),
                validatedAt =  DateValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha uma data válida."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure validatedAt is invalid`() {
        val invalidDate = LocalDateTime.of(2023, Month.SEPTEMBER, 19, 12, 0)

        factory.offerValidParameter
            .copy(
                name = StringValue("Coffee"),
                price = PriceValue(27.72),
                description = StringValue("The better coffee this city."),
                createdAt = DateValue(factory.createAt),
                validatedAt =  DateValue(invalidDate)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha uma data válida."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure name and price are invalid`() {
        val expect = listOf(
            "Preencha um valor válido.",
            "Preencha um valor maior que 0."
        )

        factory.offerValidParameter
            .copy(
                name = StringValue(""),
                price = PriceValue(0.0),
                description = StringValue("The better coffee this city."),
                createdAt = DateValue(factory.createAt),
                validatedAt =  DateValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }
}
