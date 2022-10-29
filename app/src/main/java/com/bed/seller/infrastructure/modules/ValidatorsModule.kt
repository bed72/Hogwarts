package com.bed.seller.infrastructure.modules

import org.koin.dsl.module

import com.bed.seller.domain.usecases.validator.ValidatorUseCase

import com.bed.seller.data.client.ValidatorClient
import com.bed.seller.data.usecases.validator.RemoteValidatorUseCase

import com.bed.seller.infrastructure.validator.adapters.ValidatorAdapter

fun adapterValidatorModule() = module {
    single<ValidatorClient> {
        ValidatorAdapter()
    }
}

fun validatorsUseCaseModule() = module {
    single<ValidatorUseCase> {
        RemoteValidatorUseCase(get<ValidatorClient>())
    }
}
