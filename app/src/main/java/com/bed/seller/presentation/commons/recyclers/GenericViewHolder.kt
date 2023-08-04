package com.bed.seller.presentation.commons.recyclers

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class GenericViewHolder<T>(item: ViewBinding) : RecyclerView.ViewHolder(item.root) {
    abstract fun bind(data: T)
}
