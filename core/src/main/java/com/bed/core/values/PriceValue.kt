package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import java.text.DecimalFormat

@JvmInline
value class PriceValue(val value: Double) : ValueObject<PriceValue> {
    override fun validate(): Either<String, PriceValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> = when {
        value.isNaN() -> false to Values.INVALID_PRICE.value
        value <= 0 -> false to Values.INVALID_PRICE_NEEDS_BIGGER_ZERO.value
        else -> true to toPrice()
    }
}

fun PriceValue.toPrice(): String = DecimalFormat("R$ #,###,##0.00").format(value)

fun PriceValue.fromPrice(data: String): Double {
    val pattern = "[^0-9 ]".toRegex()

    return data
        .replace(pattern, "")
        .toDouble()
        .div(100)
}
