package com.bed.seller.presentation.ui.auth.commons

import com.bed.seller.R

import androidx.annotation.StringRes

import com.bed.seller.domain.usecases.storage.StorageUseCase

import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity

interface Auth {
    fun mapper(status: Int): Int
    suspend fun saveInStorage(vararg data: Pair<String, String>)

    sealed class Actions {
        data class SignUp(val params: AuthBodyRequestEntity) : Actions()
    }

    sealed class States {
        object Loading : States()
        data class Failure(@StringRes val message: Int) : States()
        data class Success(val data: AuthResponseEntity, @StringRes val message: Int) : States()
    }
}

class AuthCommon(private val storageUseCase: StorageUseCase): Auth {

    override suspend fun saveInStorage(vararg data: Pair<String, String>) {
        for (value in data) {
            storageUseCase.save(value.first, value.second)
        }
    }

    override fun mapper(status: Int): Int =
        when (status) {
            400 -> R.string.generic_failure_message_email_already_registered
            else -> R.string.generic_failure_message
        }
}
