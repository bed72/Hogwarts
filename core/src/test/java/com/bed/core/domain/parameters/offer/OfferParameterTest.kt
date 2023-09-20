package com.bed.core.domain.parameters.offer

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

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
            assertEquals(factory.validateAt, data.validateAt.value)
        }
    }
}
