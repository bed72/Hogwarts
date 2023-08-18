package com.bed.seller.presentation.ui.dashboard.exit

import android.os.Bundle
import android.view.View

import com.bed.seller.databinding.ExitFragmentBinding
import com.bed.seller.presentation.commons.extensions.closeApplication
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

class DashboardExitFragment : BaseBottomSheetDialogFragment<ExitFragmentBinding>(
    ExitFragmentBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        setupButtonCloseApp()
        setupButtonCloseBottomSheet()
    }

    private fun setupButtonCloseApp() {
        binding.yesButton.setOnClickListener { it.closeApplication() }
    }

    private fun setupButtonCloseBottomSheet() {
        binding.noButton.setOnClickListener { dismiss() }
    }
}
