package com.bed.core.domain.parameters.offer

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate

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
    override fun isValid(): Either<List<String>, OfferParameter> = either {
        val nameIsValid = name.validate()
        val priceIsValid = price.validate()
        val descriptionIsValid = description.validate()
        val createdAtIsValid = createdAt.validate()
        val validateAtIsValid = createdAt.validate()

        zipOrAccumulate(
            { before, after -> combine(before, after) },
            { ensure(nameIsValid.isRight()) { prepare(nameIsValid.leftOrNull()) } },
            { ensure(priceIsValid.isRight()) { prepare(priceIsValid.leftOrNull()) } },
            { ensure(descriptionIsValid.isRight()) { prepare(descriptionIsValid.leftOrNull()) } },
            { ensure(createdAtIsValid.isRight()) { prepare(createdAtIsValid.leftOrNull()) } },
            { ensure(validateAtIsValid.isRight()) { prepare(validateAtIsValid.leftOrNull()) } },
        ) { _, _, _, _, _ -> OfferParameter(name, price, description, createdAt, validateAt) }
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
