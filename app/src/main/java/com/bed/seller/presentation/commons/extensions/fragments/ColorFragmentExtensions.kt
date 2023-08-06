package com.bed.seller.presentation.commons.extensions.fragments

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

import android.widget.TextView
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.animation.ObjectAnimator

fun Fragment.getColor(@ColorRes id: Int): Int =
    requireActivity().resources.getColor(id, null)

fun Fragment.setStatusBarColor(@ColorRes id: Int) {
    requireActivity().window.statusBarColor = requireActivity().getColor(id)
}

fun Fragment.setNavigationBarColor(@ColorRes id: Int) {
    requireActivity().window.navigationBarColor = requireActivity().getColor(id)
}

fun Fragment.startColorAnimation(view: TextView, fromColor: Int, toColor: Int) {
    ObjectAnimator.ofInt(
        view,
        "textColor",
        fromColor,
        toColor
    ).apply {
        duration = 6000
        setEvaluator(ArgbEvaluator())
        repeatMode = ValueAnimator.REVERSE
        repeatCount = ValueAnimator.REVERSE
        start()
    }
}
