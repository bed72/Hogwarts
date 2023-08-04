package com.bed.seller.presentation.ui.authentication.exit

import android.os.Bundle
import android.view.View
import com.bed.seller.databinding.ExitFragmentBinding
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class AuthExitFragment : BaseBottomSheetDialogFragment<ExitFragmentBinding>(
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
        binding.yesButton.setOnClickListener { exitProcess(0) }
    }

    private fun setupButtonCloseBottomSheet() {
        binding.noButton.setOnClickListener { dismiss() }
    }
}
