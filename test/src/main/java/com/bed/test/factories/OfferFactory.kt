package com.bed.test.factories

import arrow.core.left
import arrow.core.right

import java.time.Month
import java.time.LocalDateTime

import com.bed.core.values.Price
import com.bed.core.values.CreatedAt
import com.bed.core.values.Description
import com.bed.core.values.ProductName

import com.bed.core.domain.parameters.offer.OfferParameter
import com.bed.core.values.ValidatedAt

class OfferFactory {
    val createAt: LocalDateTime get() = LocalDateTime.of(2072, Month.JUNE, 27, 12, 0)
    val validateAt: LocalDateTime get() = LocalDateTime.of(2072, Month.JULY, 27, 12, 0)

    val offerValidParameter = OfferParameter(
        ProductName("Coffee"),
        Price(27.72),
        Description("The better coffee this city."),
        CreatedAt(createAt),
        ValidatedAt(validateAt)
    )

    val offerInvalidParameter = OfferParameter(
        ProductName(""),
        Price(0.0),
        Description(""),
        CreatedAt(LocalDateTime.now()),
        ValidatedAt(LocalDateTime.of(1997, Month.JULY, 3, 10, 27))
    )

    val failure get() = create(Mock.Failure)
    val success get() = create(Mock.Success)

    private fun create(mock: Mock) = when (mock) {
        Mock.Failure -> failureMessage.left()
        Mock.Success -> offerValidParameter.right()
    }
}
