package com.bed.seller.presentation.ui.auth.commons

import com.bed.seller.R

import com.bed.seller.domain.usecases.storage.GetStorageUseCase
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase

class AuthCommon(
    private val getStorageUseCase: GetStorageUseCase,
    private val saveStorageUseCase: SaveStorageUseCase
): Auth {

    override suspend fun getInStorage(key: String) = getStorageUseCase(key)

    override suspend fun saveInStorage(vararg data: Pair<String, String>) {
        for (value in data) {
            saveStorageUseCase(SaveStorageUseCase.Params(value.first to value.second))
        }
    }

    override fun mapper(status: Int): Int =
        when (status) {
            400 -> R.string.generic_failure_message_email_already_registered
            else -> R.string.generic_failure_message
        }
}
