package com.bed.seller.presentation.ui.common.fragment

import com.bed.seller.R

import android.os.Bundle

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<viewBinding : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: viewBinding? = null
    protected val binding get() = _binding!!

    protected abstract fun getViewBinding(): viewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = getViewBinding()
    }

    override fun getTheme() = R.style.ModalBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = _binding?.root

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
