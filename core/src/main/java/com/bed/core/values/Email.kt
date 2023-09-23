package com.bed.core.values

import am.ik.yavi.core.Validator
import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations

@JvmInline
value class Email(val value: String) : ValueObject {
    private val valid: Validator<Email> get() = validator {
        Email::value {
            email().message("Preencha um e-mail válido.")
            notEmpty().message("O e-mail não pode ser nulo.")
        }
    }

    override val isValid: Boolean get() = of.isValid
    override val of: ConstraintViolations get() = valid.validate(this)
    override val message: String? get() = if (of.size == 0) null else of.violations().first().message()
}
