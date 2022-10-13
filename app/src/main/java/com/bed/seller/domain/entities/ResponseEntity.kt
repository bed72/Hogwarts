package com.bed.seller.domain.entities

data class ResponseEntity<out T>(
    val status: Int,
    val data: T
)


