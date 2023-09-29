package com.bed.core.values

import am.ik.yavi.core.Validator
import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations

@JvmInline
value class Password(val value: String) : ValueObject {
    private val valid: Validator<Password> get() = validator {
        Password::value {
            notEmpty().message("Preencha uma senha válida.")
            lessThanOrEqual(16).message("A senha precisa ser menor que 16 caracteres.")
            greaterThanOrEqual(4).message("A senha precisa ser maior que 4 caracteres.")
            pattern("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+\$")
                .message("A senha presica conter caracteres maiúsculos, minúsculas e números.")
        }
    }

    override val isValid: Boolean get() = of.isValid
    override val of: ConstraintViolations get() = valid.validate(this)
    override val message: String? get() = if (of.size == 0) null else of.violations().first().message()
}
