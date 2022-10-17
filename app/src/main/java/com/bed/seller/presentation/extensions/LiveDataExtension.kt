package com.bed.seller.presentation.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.LifecycleOwner

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner) {
        it?.let(observer)
    }
}
