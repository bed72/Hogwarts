package com.bed.core.values

import am.ik.yavi.core.Validator
import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations

@JvmInline
value class UserName(val value: String) : ValueObject {
    private val valid: Validator<UserName>
        get() = validator {
            UserName::value {
            notEmpty().message("Preencha um nome e sobrenome válidos.")
            lessThanOrEqual(31).message("O nome e sobrenome precisam ser menor que 32 caracteres.")
            greaterThanOrEqual(2).message("O nome e sobrenome precisam ser maior que 2 caracteres.")
            pattern("\\b[A-Za-zÀ-ú][A-Za-zÀ-ú]+,?\\s[A-Za-zÀ-ú][A-Za-zÀ-ú]{2,31}\\b")
                    .message("Preencha um nome e sobrenome válidos.")
        }
    }

    override val isValid: Boolean get() = of.isValid
    override val of: ConstraintViolations get() = valid.validate(this)
    override val message: String? get() = if (of.size == 0) null else of.violations().first().message()
}
