package com.bed.test.factories

import com.bed.core.domain.models.failure.MessageModel

sealed class Mock {
    data object Success : Mock()
    data object Failure : Mock()
}

internal val failureMessage = MessageModel("Ops, um erro aconteceu.")
