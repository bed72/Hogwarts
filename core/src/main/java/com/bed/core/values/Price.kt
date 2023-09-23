package com.bed.core.values

import java.text.DecimalFormat

import am.ik.yavi.core.Validator
import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations

@JvmInline
value class Price(val value: Double) : ValueObject {
    private val valid: Validator<Price> get() = validator {
        Price::value {
            notNull().message("O preço não pode ser nulo.")
            positive().message("Preencha um valor maior que R\$ 0,0.")
        }
    }

    val toPrice: String get() = DecimalFormat("R$ #,###,##0.00").format(value)
    fun Price.fromPrice(data: String): Double {
        val pattern = "[^0-9 ]".toRegex()

        return data
            .replace(pattern, "")
            .toDouble()
            .div(100)
    }

    override val isValid: Boolean get() = of.isValid
    override val of: ConstraintViolations get() = valid.validate(this)
    override val message: String? get() = if (of.size == 0) null else of.violations().first().message()
}
