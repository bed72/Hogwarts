package com.bed.seller.presentation.ui.dashboard.home.model

import android.net.Uri

import com.bed.seller.presentation.commons.recyclers.ListItem

data class HomeScreenModel(
    val id: Int,
    val image: Uri,
    override val key: Long = id.toLong()
) : ListItem
