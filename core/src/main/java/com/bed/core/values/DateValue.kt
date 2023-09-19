package com.bed.core.values

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JvmInline
value class DateValue(val value: LocalDateTime) : ValueObject<DateValue> {
    override fun validate(): Either<String, DateValue> {
        val (isValid, message) = rule()

        return if (isValid) this.right() else message.left()
    }

    private fun rule(): Pair<Boolean, String> {

        return when {
            value < LocalDateTime.now().withHour(MIDNIGHT) -> false to "Preencha uma data válida."
            else -> true to toDate()
        }
    }

    companion object {
        private const val MIDNIGHT = 0
    }
}

fun DateValue.toDate(): String = value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
