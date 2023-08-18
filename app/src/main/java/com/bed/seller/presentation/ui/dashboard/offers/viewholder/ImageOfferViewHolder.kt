package com.bed.seller.presentation.ui.dashboard.offers.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater

import com.bed.seller.databinding.ItemImageComponentBinding

import com.bed.seller.presentation.commons.loaders.ImageLoader
import com.bed.seller.presentation.commons.recyclers.GenericViewHolder
import com.bed.seller.presentation.ui.dashboard.offers.model.ImageOfferScreenModel

class ImageOfferViewHolder(
    binding: ItemImageComponentBinding,
    private val imageLoader: ImageLoader
) : GenericViewHolder<ImageOfferScreenModel>(binding) {

    private val view: ImageView = binding.itemImage

    override fun bind(data: ImageOfferScreenModel) {
        imageLoader.load(url = data.image, border = 16F, imageView = view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): ImageOfferViewHolder {
            val binding = ItemImageComponentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return ImageOfferViewHolder(binding, imageLoader)
        }
    }
}
