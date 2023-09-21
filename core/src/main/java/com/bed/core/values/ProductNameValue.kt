package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class ProductNameValue(val value: String) : ValueObject<ProductNameValue> {

    override fun validate(): Either<String, ProductNameValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> {
        val pattern = "^[A-zÀ-ú '´]+".toRegex()

        return when {
            value.length >= 16 -> false to Values.INVALID_PRODUCT_NAME_BIG.value
            value.length <= 2 -> false to Values.INVALID_PRODUCT_NAME_SMALL.value
            pattern.matches(value).not() -> false to Values.INVALID_PRODUCT_NAME.value
            else -> true to value
        }
    }
}
