package com.bed.seller.presentation.ui.dashboard.offers

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.core.usecases.coroutines.CoroutinesUseCase

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val coroutinesUseCase: CoroutinesUseCase
) : ViewModel()
