package com.bed.seller.presentation.ui.dashboard.sale

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.coroutines.CoroutinesUseCase

@HiltViewModel
class SaleViewModel @Inject constructor(
    private val coroutinesUseCase: CoroutinesUseCase
) : ViewModel()
