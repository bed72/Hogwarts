package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module

import com.bed.seller.presentation.ui.auth.commons.Auth
import com.bed.seller.presentation.ui.auth.commons.AuthCommon

import com.bed.seller.domain.usecases.storage.GetStorageUseCase
import com.bed.seller.domain.usecases.storage.SaveStorageUseCase

fun authCommonModule() = module {
    single<Auth> {
        AuthCommon(
            get<GetStorageUseCase>(),
            get<SaveStorageUseCase>()
        )
    }
}
