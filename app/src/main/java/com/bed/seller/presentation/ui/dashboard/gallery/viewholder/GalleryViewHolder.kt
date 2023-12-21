package com.bed.seller.presentation.ui.dashboard.gallery.viewholder

import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.FrameLayout
import android.view.LayoutInflater

import androidx.recyclerview.widget.RecyclerView
import androidx.constraintlayout.widget.ConstraintLayout

import com.bed.seller.R

import com.bed.seller.databinding.ItemGalleryComponentBinding

import com.bed.seller.presentation.commons.extensions.load
import com.bed.seller.presentation.commons.states.ConstantStates.GONE
import com.bed.seller.presentation.commons.states.ConstantStates.VISIBLE

import com.bed.seller.presentation.ui.dashboard.gallery.model.FromGalleryScreenModel

class GalleryViewHolder(
    item: ItemGalleryComponentBinding,
) : RecyclerView.ViewHolder(item.root) {
    private var position = RecyclerView.NO_POSITION

    private val badge: FrameLayout = item.itemBadge
    private val image: ImageView = item.itemGalleryImage
    private val container: ConstraintLayout = item.itemContainerLayout

    fun bind(data: FromGalleryScreenModel) {
        image.load(data.url, 38F)

        container.setOnClickListener {
            handlerSelectedImagesStyle()
            handlerSelectedImages(data.url)

            data.onSelectedImages(selectedImages)

            position = updatePosition()
        }
    }

    private fun handlerSelectedImages(url: Uri) {
        if (isSamePosition()) selectedImages.remove(url) else selectedImages.add(url)
    }

    private fun handlerSelectedImagesStyle() {
        if (isSamePosition()) {
            badge.visibility = GONE
            container.setBackgroundResource(R.drawable.no_border_background)
        } else {
            badge.visibility = VISIBLE
            container.setBackgroundResource(R.drawable.normal_border_transparent_background)
        }
    }

    private fun isSamePosition() = position == layoutPosition

    private fun updatePosition() = if (isSamePosition()) RecyclerView.NO_POSITION else layoutPosition

    companion object {
        private val selectedImages = mutableSetOf<Uri>()

        fun create(parent: ViewGroup): GalleryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val item = ItemGalleryComponentBinding.inflate(inflater, parent, false)

            return GalleryViewHolder(item)
        }
    }
}
