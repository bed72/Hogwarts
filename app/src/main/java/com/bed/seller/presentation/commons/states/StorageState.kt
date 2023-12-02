package com.bed.seller.presentation.commons.states

import com.bed.seller.framework.constants.StorageConstant

import com.bed.core.usecases.storage.SaveStorageUseCase
import com.bed.core.domain.models.authentication.AuthenticationModel

class StorageState(
    private val saveStorageUseCase: SaveStorageUseCase
) {
    fun save(parameter: AuthenticationModel) {
        listOf(
            StorageConstant.DATASTORE_UID_USER.value to parameter.uid,
            StorageConstant.DATASTORE_EMAIL_USER.value to parameter.email
        ).run {
            forEach { saveStorageUseCase(it)  }
        }
    }
}
