package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

@JvmInline
value class CodeValue(val value: String) : ValueObject<CodeValue> {

    override fun validate(): Either<String, CodeValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> =
        when {
            value.isBlank() -> false to Values.INVALID_CODE.value
            else -> true to value
        }
}
