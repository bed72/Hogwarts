package com.bed.seller.presentation.commons.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes

import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation

import com.bed.seller.R

fun ImageView.load(
    url: Any,
    border: Float = 0.0F,
    @DrawableRes fallback: Int = R.drawable.ic_icon_toolbar,
    @DrawableRes placeholder: Int = R.drawable.ic_icon_toolbar,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    val request = ImageRequest.Builder(context)
        .data(url)
        .target(this)
        .fallback(fallback)
        .placeholder(placeholder)
        .transformations(RoundedCornersTransformation(border))
        .apply(builder)
        .build()

    context.imageLoader.enqueue(request)
}
