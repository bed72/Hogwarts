package com.bed.seller.presentation.commons.dialogs

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun dialogAlertDialog(
    context: Context,
    @LayoutRes view: Int,
    isCancelable: Boolean = false
): AlertDialog =
    MaterialAlertDialogBuilder(context)
        .setCancelable(isCancelable)
        .setView(view)
        .create()
