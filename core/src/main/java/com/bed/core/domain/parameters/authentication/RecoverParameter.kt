package com.bed.core.domain.parameters.authentication

import com.bed.core.values.Email

import com.bed.core.domain.parameters.Parameter

data class RecoverParameter(
    val email: Email = Email(""),
) : Parameter {
    override fun hasMessages(): MutableSet<String?> = mutableSetOf(email.message).apply {
        removeIf { it == null }
    }
}
