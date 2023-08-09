package com.bed.seller.presentation.ui.dashboard.home.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater

import com.bed.seller.databinding.ItemImageComponentBinding
import com.bed.seller.presentation.commons.loaders.ImageLoader

import com.bed.seller.presentation.ui.dashboard.home.model.HomeScreenModel
import com.bed.seller.presentation.commons.recyclers.GenericViewHolder

class HomeViewHolder(
    binding: ItemImageComponentBinding,
    private val imageLoader: ImageLoader
) : GenericViewHolder<HomeScreenModel>(binding) {

    private val view: ImageView = binding.itemImage

    override fun bind(data: HomeScreenModel) {
        imageLoader.load(url = data.image, border = 16F, imageView = view)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): HomeViewHolder {
            val binding = ItemImageComponentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return HomeViewHolder(binding, imageLoader)
        }
    }
}
