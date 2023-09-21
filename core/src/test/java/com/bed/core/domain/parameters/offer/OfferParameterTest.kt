package com.bed.core.domain.parameters.offer

import java.time.Month
import java.time.LocalDateTime

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import com.bed.core.values.PriceValue
import com.bed.core.values.CreatedAtValue
import com.bed.core.values.DescriptionValue
import com.bed.core.values.ProductNameValue
import com.bed.core.values.ValidatedAtValue

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
            "O nome do produto precisa ser maior que 2 caracteres.",
            "Preencha um valor maior que R\$ 0.",
            "A descrição precisam ser maior que 2 caracteres.",
            "Preencha uma data válida.",
            "A data precisa ser a partir de amanhã."
        )

        factory.offerValidParameter
            .copy(
                name = ProductNameValue(""),
                price = PriceValue(0.0),
                description = DescriptionValue(""),
                createdAt = CreatedAtValue(invalidDate),
                validatedAt =  ValidatedAtValue(invalidDate)
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure name is invalid`() {
        factory.offerValidParameter
            .copy(
                name = ProductNameValue(""),
                price = PriceValue(27.72),
                description = DescriptionValue("The better coffee this city."),
                createdAt = CreatedAtValue(factory.createAt),
                validatedAt =  ValidatedAtValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("O nome do produto precisa ser maior que 2 caracteres."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure price is invalid`() {
        factory.offerValidParameter
            .copy(
                name = ProductNameValue("Coffee"),
                price = PriceValue(0.0),
                description = DescriptionValue("The better coffee this city."),
                createdAt = CreatedAtValue(factory.createAt),
                validatedAt =  ValidatedAtValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha um valor maior que R\$ 0."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure description is invalid`() {
        factory.offerValidParameter
            .copy(
                name = ProductNameValue("Coffee"),
                price = PriceValue(27.72),
                description = DescriptionValue(""),
                createdAt = CreatedAtValue(factory.createAt),
                validatedAt =  ValidatedAtValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("A descrição precisam ser maior que 2 caracteres."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure createdAt is invalid`() {
        val invalidDate = LocalDateTime.of(2023, Month.SEPTEMBER, 19, 12, 0)

        factory.offerValidParameter
            .copy(
                name = ProductNameValue("Coffee"),
                price = PriceValue(27.72),
                description = DescriptionValue("The better coffee this city."),
                createdAt = CreatedAtValue(invalidDate),
                validatedAt =  ValidatedAtValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("Preencha uma data válida."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure validatedAt is invalid`() {
        val invalidDate = LocalDateTime.of(2023, Month.SEPTEMBER, 19, 12, 0)

        factory.offerValidParameter
            .copy(
                name = ProductNameValue("Coffee"),
                price = PriceValue(27.72),
                description = DescriptionValue("The better coffee this city."),
                createdAt = CreatedAtValue(factory.createAt),
                validatedAt =  ValidatedAtValue(invalidDate)
            )
            .isValid()
            .mapLeft { message -> assertEquals(listOf("A data precisa ser a partir de amanhã."), message) }
    }

    @Test
    fun `Should try validate Offer Parameter return failure name and price are invalid`() {
        val expect = listOf(
            "O nome do produto precisa ser maior que 2 caracteres.",
            "Preencha um valor maior que R\$ 0."
        )

        factory.offerValidParameter
            .copy(
                name = ProductNameValue(""),
                price = PriceValue(0.0),
                description = DescriptionValue("The better coffee this city."),
                createdAt = CreatedAtValue(factory.createAt),
                validatedAt =  ValidatedAtValue(factory.validateAt)
            )
            .isValid()
            .mapLeft { message -> assertEquals(expect, message) }
    }
}
