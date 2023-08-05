package com.bed.seller.presentation.ui.dashboard.permission.viewholder

import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater

import com.bed.seller.databinding.ItemPermissionComponentBinding

import com.bed.seller.presentation.commons.recyclers.GenericViewHolder
import com.bed.seller.presentation.ui.dashboard.permission.model.PermissionModel

class PermissionViewHolder(
    itemPermissionComponentBinding: ItemPermissionComponentBinding
) : GenericViewHolder<PermissionModel>(itemPermissionComponentBinding) {

    private val description: TextView = itemPermissionComponentBinding.itemPermissionText

    override fun bind(data: PermissionModel) {
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
