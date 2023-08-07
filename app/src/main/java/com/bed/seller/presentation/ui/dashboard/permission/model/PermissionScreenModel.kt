package com.bed.seller.presentation.ui.dashboard.permission.model

import com.bed.seller.presentation.commons.recyclers.ListItem

data class PermissionScreenModel(
    val id: Int,
    val description: String,
    override val key: Long = id.toLong()
) : ListItem
