package com.bed.seller.infrastructure.modules

import org.koin.dsl.module

import com.bed.seller.data.client.security.SecurityClient

import com.bed.seller.infrastructure.security.adapter.SecurityAdapter

fun adapterSecurityModule() = module {
    single<SecurityClient> {
        SecurityAdapter()
    }
}
