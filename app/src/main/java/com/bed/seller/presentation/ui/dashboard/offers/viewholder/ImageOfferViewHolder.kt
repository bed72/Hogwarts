package com.bed.seller.presentation.ui.dashboard.offers.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater

import com.bed.seller.databinding.ItemImageComponentBinding

import com.bed.seller.presentation.commons.extensions.load
import com.bed.seller.presentation.commons.recyclers.GenericViewHolder

import com.bed.seller.presentation.ui.dashboard.offers.model.ImageOfferScreenModel

class ImageOfferViewHolder(
    binding: ItemImageComponentBinding,
) : GenericViewHolder<ImageOfferScreenModel>(binding) {

    private val view: ImageView = binding.itemGalleryImage

    override fun bind(data: ImageOfferScreenModel) {
        view.load(data.image, 16F)
    }

    companion object {
        fun create(parent: ViewGroup): ImageOfferViewHolder {
            val binding = ItemImageComponentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return ImageOfferViewHolder(binding)
        }
    }
}
