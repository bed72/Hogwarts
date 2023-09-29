package com.bed.core.domain.parameters.offer

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
) : Parameter {
    override fun hasMessages(): MutableSet<String?> =
        mutableSetOf(
            name.message,
            price.message,
            description.message,
            createdAt.message,
            validatedAt.message
        ).apply { removeIf { it == null } }
}
