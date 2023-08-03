package com.bed.seller.presentation.commons.fragments

import com.bed.seller.R

import android.os.Bundle

import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias InflateBottomSheet<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseBottomSheetDialogFragment<viewBinding : ViewBinding>(
    private val inflate: InflateBottomSheet<viewBinding>
) : BottomSheetDialogFragment() {

    private var _binding: viewBinding? = null
    protected val binding: viewBinding get() = _binding!!

    override fun getTheme() = R.style.BottomSheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
