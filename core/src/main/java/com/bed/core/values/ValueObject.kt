package com.bed.core.values

import arrow.core.Either

sealed interface ValueObject<out T> {
    fun validate(): Either<String, T>
}
