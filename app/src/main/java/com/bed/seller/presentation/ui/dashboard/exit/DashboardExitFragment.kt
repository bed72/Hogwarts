package com.bed.seller.presentation.ui.dashboard.exit

import android.os.Bundle
import android.view.View

import com.bed.seller.R

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
        setupDescription()
        setupButtons()
    }

    private fun setupDescription() {
        binding.descriptionText.text = getText(R.string.exit_description)
    }

    private fun setupButtons() {
        with (binding) {
            yesButton.setOnClickListener { it.closeApplication() }
            noButton.setOnClickListener { dismiss() }
        }
    }
}
