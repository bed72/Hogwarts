package com.bed.seller.presentation.commons.recyclers

interface ListItem {
    val key: Long

    fun areContentsTheSame(other: ListItem) = this == other
    fun areItemsTheSame(other: ListItem) = this.key == other.key
}
