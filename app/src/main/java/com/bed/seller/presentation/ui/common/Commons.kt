package com.bed.seller.presentation.ui.common

interface Commons {
    fun mapper(status: Int): Int

    companion object {
        const val CLEAR = ""
        const val EMPTY = 0
        const val LOADING = 1
        const val SUCCESS = 1
        const val FAILURE = 2
        const val FORM_VALID = true
        const val FORM_INVALID = false
    }
}
