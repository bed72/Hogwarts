package com.bed.seller.presentation.ui.dashboard.gallery.viewholder

import android.view.ViewGroup
import android.view.LayoutInflater

import androidx.recyclerview.widget.RecyclerView

import com.bed.seller.databinding.ItemCameraComponentBinding

import com.bed.seller.presentation.ui.dashboard.gallery.model.FromCameraScreenModel

class CameraViewHolder(
    item: ItemCameraComponentBinding,
) : RecyclerView.ViewHolder(item.root) {

    fun bind(data: FromCameraScreenModel) {
        itemView.setOnClickListener { data.onClick() }
    }

    companion object {
        fun create(parent: ViewGroup): CameraViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val item = ItemCameraComponentBinding.inflate(inflater, parent, false)

            return CameraViewHolder(item)
        }
    }
}
