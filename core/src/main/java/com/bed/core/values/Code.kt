package com.bed.core.values

import am.ik.yavi.core.Validator
import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations

@JvmInline
value class Code(val value: String) : ValueObject {
    private val valid: Validator<Code> get() = validator {
        Code::value {
            notEmpty().message("O código não é válido.")
        }
    }

    override val isValid: Boolean get() = of.isValid
    override val of: ConstraintViolations get() = valid.validate(this)
    override val message: String? get() = if (of.size == 0) null else of.violations().first().message()
}
