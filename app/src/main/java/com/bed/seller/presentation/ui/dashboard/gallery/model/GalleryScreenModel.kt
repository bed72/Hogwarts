package com.bed.seller.presentation.ui.dashboard.gallery.model

import android.net.Uri

interface GalleryScreenModel

data class FromCameraScreenModel(val onClick: () -> Unit) : GalleryScreenModel

data class FromGalleryScreenModel(
    val url: Uri,
    val onSelectedImages: (MutableSet<Uri>) -> Unit
) : GalleryScreenModel
