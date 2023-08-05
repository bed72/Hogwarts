package com.bed.seller.presentation.ui.dashboard.sale

import android.os.Bundle
import android.view.View

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.databinding.SaleFragmentBinding

import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class SaleFragment : BaseBottomSheetDialogFragment<SaleFragmentBinding>(SaleFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        setupCancel()
    }

    private fun setupCancel() {
        binding.saleCancelButton.setOnClickListener {
            dismiss()
        }
    }
}
