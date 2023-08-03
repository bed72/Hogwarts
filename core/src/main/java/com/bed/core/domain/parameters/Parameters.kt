package com.bed.core.domain.parameters

import arrow.core.Either

abstract class Parameters<out T> {
    abstract fun isValid(): Either<List<String>, T>

    companion object {
        fun prepare(message: String?) = if (message.isNullOrBlank()) listOf("") else listOf(message)

        fun combine(before: List<String>, after: List<String>): List<String> =
            if (before.isEmpty() and after.isEmpty()) listOf() else before.plus(after)
    }
}