package com.bed.seller.presentation.ui.dashboard.gallery.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater

import androidx.recyclerview.widget.RecyclerView

import com.bed.seller.databinding.ItemGalleryComponentBinding

import com.bed.seller.presentation.commons.extensions.load
import com.bed.seller.presentation.ui.dashboard.gallery.model.FromGalleryScreenModel

class GalleryViewHolder(
    item: ItemGalleryComponentBinding,
) : RecyclerView.ViewHolder(item.root) {

    private val view: ImageView = item.itemGalleryImage

    fun bind(data: FromGalleryScreenModel) {
        view.load(data.url, 32F)

        itemView.setOnClickListener { data.onClick() }
    }

    companion object {
        fun create(parent: ViewGroup): GalleryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val item = ItemGalleryComponentBinding.inflate(inflater, parent, false)

            return GalleryViewHolder(item)
        }
    }
}
