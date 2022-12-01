package com.bed.seller.infrastructure.modules

import org.koin.dsl.module

import com.bed.seller.domain.dispatchers.Coroutines
import com.bed.seller.domain.dispatchers.AppCoroutines

fun dispatchersModule() = module {
    single<Coroutines> {
        AppCoroutines()
    }
}
