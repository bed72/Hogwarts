package com.bed.seller.presentation.commons.extensions

import android.os.Build
import android.view.View
import android.view.WindowInsets

import kotlin.system.exitProcess

import androidx.annotation.RequiresApi
import android.animation.ObjectAnimator

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

fun View.closeApplication() {
    exitProcess(0)
}

fun View.slideUp(height: Float) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, height, 0f).apply {
        duration = 600
        start()
    }
}

fun View.slideDown(height: Float) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, height).apply {
        duration = 1000
        start()
    }
}
