package com.bed.core.values

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import am.ik.yavi.core.Validator
import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations

@JvmInline
value class CreatedAt(val value: LocalDateTime) : ValueObject {
    private val valid: Validator<CreatedAt> get() = validator {
        CreatedAt::value {
            after {
                LocalDateTime.now().withHour(0).withMinute(0)
            }.message("A data não corresponde ao dia de hoje.")
            before {
                LocalDateTime.now().withHour(23).withMinute(59)
            }.message("A data não corresponde ao dia de hoje.")
        }
    }
    val toDate: String get() = value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    override val isValid: Boolean get() = of.isValid
    override val of: ConstraintViolations get() = valid.validate(this)
    override val message: String? get() = if (of.size == 0) null else of.violations().first().message()
}
