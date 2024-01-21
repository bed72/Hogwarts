package com.bed.seller.presentation.commons.extensions

import com.bed.seller.presentation.commons.states.States

fun <S> States<S>.observePairStates(
    success: (data: S) -> Unit,
    failure: (data: String) -> Unit
) {
    when (this) {
        States.Initial -> {}
        States.Loading -> {}
        is States.Success -> success(this.data)
        is States.Failure -> failure(this.data)
    }
}

fun <S> States<S>.observeTripleStates(
    loading: () -> Unit,
    success: (data: S) -> Unit,
    failure: (data: String) -> Unit
) {
    when (this) {
        States.Initial -> {}
        States.Loading -> loading()
        is States.Success -> success(this.data)
        is States.Failure -> failure(this.data)
    }
}
