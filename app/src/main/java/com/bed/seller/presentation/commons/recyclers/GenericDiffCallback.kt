package com.bed.seller.presentation.commons.recyclers

import androidx.recyclerview.widget.DiffUtil

class GenericDiffCallback<T : ListItem> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.areContentsTheSame(newItem)
}
