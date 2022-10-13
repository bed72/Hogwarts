package com.bed.seller.infrastructure.modules

import org.koin.dsl.module

import com.bed.seller.domain.dispatchers.CoroutinesDispatchers
import com.bed.seller.domain.dispatchers.AppCoroutinesDispatchers

fun dispatchersModule() = module {
    single<CoroutinesDispatchers> {
        AppCoroutinesDispatchers()
    }
}
