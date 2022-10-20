package com.bed.seller.infrastructure.modules.auth

import org.koin.dsl.module

import com.bed.seller.presentation.ui.common.Commons
import com.bed.seller.presentation.ui.common.AppCommons

fun authCommonModule() = module {
    single<Commons> {
        AppCommons()
    }
}
