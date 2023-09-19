package com.bed.core.domain.parameters.offer

import arrow.core.Either

import java.time.LocalDateTime

import com.bed.core.values.DateValue
import com.bed.core.values.PriceValue
import com.bed.core.values.StringValue

import com.bed.core.domain.parameters.Parameter

data class OfferParameter(
    val name: StringValue,
    val price: PriceValue,
    val description: StringValue,
    val createdAt: DateValue,
    val validateAt: DateValue
) : Parameter<OfferParameter>() {
    override fun isValid(): Either<List<String>, OfferParameter> {
        TODO("Not yet implemented")
    }

    companion object {
        operator fun invoke() =
            OfferParameter(
                StringValue(""),
                PriceValue(0.0),
                StringValue(""),
                DateValue(LocalDateTime.now()),
                DateValue(LocalDateTime.now())
            )
    }
}
