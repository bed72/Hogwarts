package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JvmInline
value class ValidatedAtValue(val value: LocalDateTime) : ValueObject<ValidatedAtValue> {
    override fun validate(): Either<String, ValidatedAtValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> = when {
        value.isBefore(LocalDateTime.now().withHour(23).withMinute(59)) ->
                false to Values.INVALID_VALIDATED_AT.value
        else -> true to toDate()
    }
}

fun ValidatedAtValue.toDate(): String = value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
