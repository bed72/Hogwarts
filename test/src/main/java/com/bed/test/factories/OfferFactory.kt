package com.bed.test.factories

import arrow.core.left
import arrow.core.right

import java.time.Month
import java.time.LocalDateTime

import com.bed.core.values.DateValue
import com.bed.core.values.PriceValue
import com.bed.core.values.StringValue

import com.bed.core.domain.parameters.offer.OfferParameter

class OfferFactory {
    val createAt: LocalDateTime get() = LocalDateTime.of(2772, Month.JUNE, 27, 12, 0)
    val validateAt: LocalDateTime get() = LocalDateTime.of(2772, Month.JULY, 27, 12, 0)

    val offerValidParameter = OfferParameter(
        StringValue("Coffee"),
        PriceValue(27.72),
        StringValue("The better coffee this city."),
        DateValue(createAt),
        DateValue(LocalDateTime.of(2772, Month.JULY, 27, 12, 0))
    )

    val offerInvalidParameter = OfferParameter(
        StringValue(""),
        PriceValue(0.0),
        StringValue(""),
        DateValue(LocalDateTime.now()),
        DateValue(LocalDateTime.of(1997, Month.JULY, 3, 10, 27))
    )

    val failure get() = create(Mock.Failure)
    val success get() = create(Mock.Success)

    private fun create(mock: Mock) = when (mock) {
        Mock.Failure -> failureMessage.left()
        Mock.Success -> offerValidParameter.right()
    }
}
