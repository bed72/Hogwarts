package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JvmInline
value class CreatedAtValue(val value: LocalDateTime) : ValueObject<CreatedAtValue> {
    override fun validate(): Either<String, CreatedAtValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> =
         when {
             value < LocalDateTime.now().withHour(23).withMinute(59) -> false to Values.INVALID_DATE.value
             else -> true to toDate()
         }
}

fun CreatedAtValue.toDate(): String = value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
