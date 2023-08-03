package com.bed.seller.presentation.commons.extensions

import android.app.Activity
import android.content.Context
import android.view.Window
import android.view.WindowManager

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog

import com.google.android.material.dialog.MaterialAlertDialogBuilder

// Dialogs
fun Activity.dialog(
    context: Context,
    @LayoutRes view: Int,
    isCancelable: Boolean = false
): AlertDialog =
    MaterialAlertDialogBuilder(context)
        .setCancelable(isCancelable)
        .setView(view)
        .create()


// Security
fun Activity.preventScreenshotsAndRecentAppThumbnails() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_SECURE,
        WindowManager.LayoutParams.FLAG_SECURE
    )
}
