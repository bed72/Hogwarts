package com.bed.core.domain.models.sale

import kotlinx.datetime.LocalDate

data class SaleModel(
    val id: String,
    val name: String,
    val price: Float,
    val description: String,
    val validation: LocalDate
)
