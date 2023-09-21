package com.bed.test.factories

import arrow.core.left
import arrow.core.right

import java.time.Month
import java.time.LocalDateTime

import com.bed.core.values.CodeValue
import com.bed.core.values.PriceValue
import com.bed.core.values.CreatedAtValue
import com.bed.core.values.DescriptionValue
import com.bed.core.values.ProductNameValue

import com.bed.core.domain.parameters.offer.OfferParameter
import com.bed.core.values.ValidatedAtValue

class OfferFactory {
    val createAt: LocalDateTime get() = LocalDateTime.of(2072, Month.JUNE, 27, 12, 0)
    val validateAt: LocalDateTime get() = LocalDateTime.of(2072, Month.JULY, 27, 12, 0)

    val offerValidParameter = OfferParameter(
        ProductNameValue("Coffee"),
        PriceValue(27.72),
        DescriptionValue("The better coffee this city."),
        CreatedAtValue(createAt),
        ValidatedAtValue(validateAt)
    )

    val offerInvalidParameter = OfferParameter(
        ProductNameValue(""),
        PriceValue(0.0),
        DescriptionValue(""),
        CreatedAtValue(LocalDateTime.now()),
        ValidatedAtValue(LocalDateTime.of(1997, Month.JULY, 3, 10, 27))
    )

    val failure get() = create(Mock.Failure)
    val success get() = create(Mock.Success)

    private fun create(mock: Mock) = when (mock) {
        Mock.Failure -> failureMessage.left()
        Mock.Success -> offerValidParameter.right()
    }
}
