package com.bed.core.domain.parameters.offer

import java.time.Month
import java.time.LocalDateTime

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.test.factories.OfferFactory

import com.bed.core.values.Price
import com.bed.core.values.CreatedAt
import com.bed.core.values.Description
import com.bed.core.values.ProductName
import com.bed.core.values.ValidatedAt

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
        val expect = mutableSetOf(
            "Preencha um nome de produto válido.",
            "Preencha um valor maior que R\$ 0,0.",
            "A descrição não pode ser nula.",
            "A data não corresponde ao dia de hoje.",
            "A data precisa ser após o dia de hoje."
        )

        factory.offerValidParameter
            .copy(
                name = ProductName(""),
                price = Price(0.0),
                description = Description(""),
                createdAt = CreatedAt(invalidDate),
                validatedAt =  ValidatedAt(invalidDate)
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure name is invalid`() {
        factory.offerValidParameter
            .copy(
                name = ProductName(""),
                price = Price(27.72),
                description = Description("The better coffee this city."),
                createdAt = CreatedAt(LocalDateTime.now()),
                validatedAt =  ValidatedAt(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(mutableSetOf("Preencha um nome de produto válido."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure price is invalid`() {
        factory.offerValidParameter
            .copy(
                name = ProductName("Coffee"),
                price = Price(0.0),
                description = Description("The better coffee this city."),
                createdAt = CreatedAt(LocalDateTime.now()),
                validatedAt =  ValidatedAt(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(mutableSetOf("Preencha um valor maior que R\$ 0,0."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure description is invalid`() {
        factory.offerValidParameter
            .copy(
                name = ProductName("Coffee"),
                price = Price(27.72),
                description = Description(""),
                createdAt = CreatedAt(LocalDateTime.now()),
                validatedAt =  ValidatedAt(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(mutableSetOf("A descrição não pode ser nula."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure created at is invalid`() {
        val invalidDate = LocalDateTime.of(2023, Month.SEPTEMBER, 19, 12, 0)

        factory.offerValidParameter
            .copy(
                name = ProductName("Coffee"),
                price = Price(27.72),
                description = Description("The better coffee this city."),
                createdAt = CreatedAt(invalidDate),
                validatedAt =  ValidatedAt(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(mutableSetOf("A data não corresponde ao dia de hoje."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure validated at is invalid`() {
        val invalidDate = LocalDateTime.of(2023, Month.SEPTEMBER, 19, 12, 0)

        factory.offerValidParameter
            .copy(
                name = ProductName("Coffee"),
                price = Price(27.72),
                description = Description("The better coffee this city."),
                createdAt = CreatedAt(LocalDateTime.now()),
                validatedAt =  ValidatedAt(invalidDate)
            )
            .isValid()
            .mapLeft { message -> assertEquals(mutableSetOf("A data precisa ser após o dia de hoje."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure name and price are invalid`() {
        val expect = mutableSetOf(
            "Preencha um nome de produto válido.",
            "Preencha um valor maior que R\$ 0,0."
        )

        factory.offerValidParameter
            .copy(
                name = ProductName(""),
                price = Price(0.0),
                description = Description("The better coffee this city."),
                createdAt = CreatedAt(LocalDateTime.now()),
                validatedAt =  ValidatedAt(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }
}
