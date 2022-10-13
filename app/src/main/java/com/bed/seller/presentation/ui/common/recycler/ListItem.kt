package com.bed.seller.presentation.ui.common.recycler

interface ListItem {
    val key: Long

    fun areContentsTheSame(other: ListItem) = this == other
    fun areItemsTheSame(other: ListItem) = this.key == other.key
}
