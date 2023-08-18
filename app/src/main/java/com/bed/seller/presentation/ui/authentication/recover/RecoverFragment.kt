package com.bed.seller.presentation.ui.authentication.recover

import android.os.Bundle
import android.view.View

import com.bed.seller.databinding.RecoverFragmentBinding

import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

class RecoverFragment : BaseBottomSheetDialogFragment<RecoverFragmentBinding>(
    RecoverFragmentBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        closeBottomSheet()
    }

    private fun closeBottomSheet() {
        binding.closeButton.setOnClickListener { dismiss() }
    }
}
