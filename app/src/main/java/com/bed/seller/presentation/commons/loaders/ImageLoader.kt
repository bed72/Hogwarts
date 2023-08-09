package com.bed.seller.presentation.commons.loaders

import javax.inject.Inject

import android.widget.ImageView

import androidx.annotation.DrawableRes

import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation

import com.bed.seller.R

interface ImageLoader {
    fun load(
        url: Any,
        border: Float = 0.0F,
        imageView: ImageView,
        @DrawableRes fallback: Int = R.drawable.ic_icon_toolbar,
        @DrawableRes placeholder: Int = R.drawable.ic_icon_toolbar,
        builder: ImageRequest.Builder.() -> Unit = {}
    )
}

class CoilImageLoader @Inject constructor(
    private val loader: coil.ImageLoader
) : ImageLoader {
    override fun load(
        url: Any,
        border: Float,
        imageView: ImageView,
        fallback: Int,
        placeholder: Int,
        builder: ImageRequest.Builder.() -> Unit
    ) {
        val request = ImageRequest.Builder(imageView.context)
            .data(url)
            .target(imageView)
            .fallback(fallback)
            .placeholder(placeholder)
            .transformations(RoundedCornersTransformation(border))
            .apply(builder)
            .build()

        loader.enqueue(request)
    }
}
