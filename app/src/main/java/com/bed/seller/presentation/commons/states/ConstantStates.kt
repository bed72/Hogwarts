package com.bed.seller.presentation.commons.states

object ConstantStates {
    const val FLIPPER_LOADING = 1
    const val FLIPPER_FAILURE = 0
    const val FLIPPER_SUCCESS = FLIPPER_FAILURE
}

sealed class States<out T> {
    data object Loading : States<Nothing>()
    data class Success<T>(val data: T? = null) : States<T>()
    data class Failure(val data: String? = null, var consumed: Boolean = false) : States<Nothing>()
}
