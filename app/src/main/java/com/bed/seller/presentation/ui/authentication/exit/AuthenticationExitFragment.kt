package com.bed.seller.presentation.ui.authentication.exit

import android.os.Bundle
import android.view.View

import com.bed.seller.databinding.ExitFragmentBinding

import com.bed.seller.presentation.commons.extensions.closeApplication
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

class AuthenticationExitFragment : BaseBottomSheetDialogFragment<ExitFragmentBinding>(
    ExitFragmentBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        setupButtons()
    }

    private fun setupButtons() {
        with (binding) {
            yesButton.setOnClickListener { it.closeApplication() }
            noButton.setOnClickListener { dismiss() }
        }
    }
}
