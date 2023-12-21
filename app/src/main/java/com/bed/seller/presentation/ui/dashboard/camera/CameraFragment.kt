package com.bed.seller.presentation.ui.dashboard.camera

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import dagger.hilt.android.AndroidEntryPoint

import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.bed.seller.databinding.CameraFragmentBinding
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class CameraFragment : BaseBottomSheetDialogFragment<CameraFragmentBinding>(CameraFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFullBottomSheet()
    }

    private fun setupFullBottomSheet() {
        val bottom = dialog?.findViewById<FrameLayout>(BOTTOM_SHEET)?.apply {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        BottomSheetBehavior.from(bottom!!).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            peekHeight = resources.displayMetrics.heightPixels
        }
    }

    companion object {
        private val BOTTOM_SHEET = com.google.android.material.R.id.design_bottom_sheet
    }

}
