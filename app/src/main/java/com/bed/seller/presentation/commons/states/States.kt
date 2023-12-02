package com.bed.seller.presentation.commons.states

import android.view.View

object ConstantStates {
    const val GONE = View.GONE
    const val VISIBLE = View.VISIBLE

    const val FLIPPER_LOADING = 1
    const val FLIPPER_FAILURE = 0
    const val FLIPPER_SUCCESS = FLIPPER_FAILURE
}

sealed class States<out T> {
    data object Initial : States<Nothing>()
    data object Loading : States<Nothing>()
    data class Success<T>(val data: T) : States<T>()
    data class Failure(val data: String, var consumed: Boolean = false) : States<Nothing>()
}
