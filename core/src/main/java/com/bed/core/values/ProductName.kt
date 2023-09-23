package com.bed.core.values

import am.ik.yavi.core.Validator
import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations

@JvmInline
value class ProductName(val value: String) : ValueObject {
    private val valid: Validator<ProductName> get() = validator {
        ProductName::value {
            notEmpty().message("Preencha um nome de produto válido.")
            pattern("^[A-zÀ-ú '´]+").message("Preencha um nome de produto válido.")
            lessThanOrEqual(15).message("O nome do produto precisa ser menor que 16 caracteres.")
            greaterThanOrEqual(2).message("O nome do produto precisa ser maior que 2 caracteres.")
        }
    }

    override val isValid: Boolean get() = of.isValid
    override val of: ConstraintViolations get() = valid.validate(this)
    override val message: String? get() = if (of.size == 0) null else of.violations().first().message()
}
