package com.bed.core.domain.parameters

import arrow.core.Either

interface Parameter<out T> {
    fun isValid(): Either<MutableSet<String?>, T>
}
