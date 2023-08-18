package com.bed.seller.presentation.ui.dashboard.offers.model

import android.net.Uri

import com.bed.seller.presentation.commons.recyclers.ListItem

data class ImageOfferScreenModel(
    val id: Int,
    val image: Uri,
    override val key: Long = id.toLong()
) : ListItem
