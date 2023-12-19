package com.bed.seller.presentation.ui.dashboard.gallery.adapter

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.bed.seller.presentation.ui.dashboard.gallery.model.GalleryScreenModel
import com.bed.seller.presentation.ui.dashboard.gallery.viewholder.CameraViewHolder
import com.bed.seller.presentation.ui.dashboard.gallery.model.FromCameraScreenModel
import com.bed.seller.presentation.ui.dashboard.gallery.model.FromGalleryScreenModel
import com.bed.seller.presentation.ui.dashboard.gallery.viewholder.GalleryViewHolder

class GalleryAdapter(
    private val data: List<GalleryScreenModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = if (position == CAMERA) CAMERA else GALLERY

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == CAMERA) CameraViewHolder.create(parent) else GalleryViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (data[position]) {
            is FromCameraScreenModel ->
                (holder as CameraViewHolder).bind(data[position] as FromCameraScreenModel)
            is FromGalleryScreenModel ->
                (holder as GalleryViewHolder).bind(data[position] as FromGalleryScreenModel)
        }
    }

    companion object {
        private const val CAMERA = 0
        private const val GALLERY = 1
    }
}
