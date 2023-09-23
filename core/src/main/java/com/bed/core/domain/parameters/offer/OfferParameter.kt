package com.bed.core.domain.parameters.offer

import arrow.core.Either
import arrow.core.left
import arrow.core.right

import java.time.LocalDateTime

import com.bed.core.values.Price
import com.bed.core.values.CreatedAt
import com.bed.core.values.Description
import com.bed.core.values.ProductName
import com.bed.core.values.ValidatedAt

import com.bed.core.domain.parameters.Parameter

data class OfferParameter(
    val name: ProductName = ProductName(""),
    val price: Price = Price(0.0),
    val description: Description = Description(""),
    val createdAt: CreatedAt = CreatedAt(LocalDateTime.now()),
    val validatedAt: ValidatedAt = ValidatedAt(LocalDateTime.now())
) : Parameter<OfferParameter> {
    override fun isValid(): Either<MutableSet<String?>, OfferParameter> {
        val data = mutableSetOf(
            name.message,
            price.message,
            description.message,
            createdAt.message,
            validatedAt.message
        ).apply { removeIf { it == null } }

        return if (data.all { it == null }) this.right() else data.left()
    }
}
