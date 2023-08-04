package com.bed.seller.presentation.commons.permissions

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object Permissions {
    val permissionsCommons = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val permissionsToTiramisu = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.POST_NOTIFICATIONS
    )
}
