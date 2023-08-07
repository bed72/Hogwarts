package com.bed.seller.presentation.ui.dashboard.permission.viewholder

import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater

import com.bed.seller.databinding.ItemPermissionComponentBinding

import com.bed.seller.presentation.commons.recyclers.GenericViewHolder
import com.bed.seller.presentation.ui.dashboard.permission.model.PermissionScreenModel

class PermissionViewHolder(
    binding: ItemPermissionComponentBinding
) : GenericViewHolder<PermissionScreenModel>(binding) {

    private val description: TextView = binding.itemText

    override fun bind(data: PermissionScreenModel) {
        description.text = data.description
    }

    companion object {
        fun create(parent: ViewGroup): PermissionViewHolder {
            val itemBinding = ItemPermissionComponentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return PermissionViewHolder(itemBinding)
        }
    }
}
