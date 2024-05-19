package com.bed.core.entities.value

import arrow.core.right
import arrow.core.leftNel
import arrow.core.EitherNel

@JvmInline
value class CodeValue private constructor(private val value: String) {

    operator fun invoke() = value

    companion object {
        operator fun invoke(value: String): EitherNel<String, CodeValue> =
            if(isValid(value)) CodeValue(value).right()
            else Values.INVALID_CODE.message.leftNel()

        private fun isValid(value: String): Boolean = value.isNotBlank()
    }
}
