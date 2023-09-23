package com.bed.core.values

import am.ik.yavi.core.ConstraintViolations

sealed interface ValueObject {
    val isValid: Boolean
    val message: String?
    val of: ConstraintViolations
}
