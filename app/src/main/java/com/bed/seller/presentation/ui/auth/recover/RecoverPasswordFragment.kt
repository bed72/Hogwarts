package com.bed.seller.presentation.ui.auth.recover

import android.os.Bundle
import android.view.View
import com.bed.seller.databinding.RecoverPasswordFragmentBinding
import com.bed.seller.presentation.ui.common.fragment.BaseBottomSheetDialogFragment

class RecoverPasswordFragment : BaseBottomSheetDialogFragment<RecoverPasswordFragmentBinding>() {

    override fun getViewBinding() = RecoverPasswordFragmentBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        closeBottomSheet()
    }

    private fun closeBottomSheet() {
        binding.recoverPasswordCloseButton.setOnClickListener {
            dismiss()
        }
    }
}
