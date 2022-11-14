package com.bed.seller.infrastructure.modules.home

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

import com.bed.seller.presentation.ui.account.AccountViewModel

val homeViewModelsModule = module {
    viewModel {
        AccountViewModel()
    }
}
