package com.bed.seller.presentation.commons.states

import com.bed.seller.framework.constants.StorageConstant

import com.bed.core.usecases.storage.SaveStorageUseCase
import com.bed.core.domain.models.authentication.AuthenticationModel

class StorageState(
    private val saveStorageUseCase: SaveStorageUseCase
) {

    fun save(parameter: AuthenticationModel) {
        listOf(
            StorageConstant.DATASTORE_ACCESS_TOKEN.value to parameter.accessToken,
            StorageConstant.DATASTORE_REFRESH_TOKEN.value to parameter.refreshToken
        ).run {
            forEach { saveStorageUseCase(it)  }
        }
    }

}
