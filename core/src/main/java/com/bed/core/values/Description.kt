package com.bed.core.values

import am.ik.yavi.core.Validator
import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations

@JvmInline
value class Description(val value: String) : ValueObject {
    private val valid: Validator<Description> get() = validator {
        Description::value {
            notEmpty().message("Preencha uma descrição válida.")
            pattern("^[a-zA-Z0-9\\s.,;!']+\$").message("Preencha uma descrição válida.")
            lessThanOrEqual(63).message("A descrição precisa ser menor que 64 caracteres.")
            greaterThanOrEqual(4).message("A descrição precisa ser maior que 4 caracteres.")
        }
    }

    override val isValid: Boolean get() = of.isValid
    override val of: ConstraintViolations get() = valid.validate(this)
    override val message: String? get() = if (of.size == 0) null else of.violations().first().message()
}
