package com.bed.seller.presentation.commons.extensions

import android.widget.TextView
import android.animation.ValueAnimator

fun TextView.typewriter(text: String, delay: Long = 32L) {
    if (text.isEmpty()) return

    val charAnimation = ValueAnimator.ofInt(0, text.length)

    charAnimation.apply {
        this.duration = delay * text.length.toLong()
        this.repeatCount = 0
        addUpdateListener {
            val charCount = it.animatedValue as Int
            val animatedText = text.substring(0, charCount)
            this@typewriter.text = animatedText
        }
    }

    charAnimation.start()
}
