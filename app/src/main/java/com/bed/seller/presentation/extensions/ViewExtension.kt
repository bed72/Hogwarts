package com.bed.seller.presentation.extensions

import android.os.Build
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi

// Keyboard
@RequiresApi(Build.VERSION_CODES.R)
fun View.showKeyboard(view: View) {
    windowInsetsController?.show(WindowInsets.Type.ime())
    view.requestFocus()
}

@RequiresApi(Build.VERSION_CODES.R)
fun View.hideKeyboard(view: View) {
    windowInsetsController?.hide(WindowInsets.Type.ime())
    view.clearFocus()
}
