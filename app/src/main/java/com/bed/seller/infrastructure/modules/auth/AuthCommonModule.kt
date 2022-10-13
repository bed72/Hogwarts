package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module

import com.bed.seller.domain.usecases.storage.StorageUseCase

import com.bed.seller.presentation.ui.auth.commons.Auth
import com.bed.seller.presentation.ui.auth.commons.AuthCommon

fun authCommonModule() = module {
    single<Auth> {
        AuthCommon(get<StorageUseCase>())
    }
}
