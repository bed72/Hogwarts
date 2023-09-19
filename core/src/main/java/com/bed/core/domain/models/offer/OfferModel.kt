package com.bed.core.domain.models.offer

import java.time.LocalDateTime

data class OfferModel(
    val id: String,
    val name: String,
    val price: Float,
    val description: String,
    val createdAt: LocalDateTime,
    val validateAt: LocalDateTime
)
