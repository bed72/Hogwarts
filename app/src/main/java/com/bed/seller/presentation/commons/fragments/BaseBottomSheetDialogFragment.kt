package com.bed.seller.presentation.commons.fragments

import android.os.Bundle

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import androidx.viewbinding.ViewBinding

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.bed.seller.R

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

//    protected fun setNavigationBarColorTheme(
//        @ColorRes colorDark: Int = R.color.dark_background,
//        @ColorRes colorLight: Int = R.color.light_background
//    ) {
//        when (requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//            Configuration.UI_MODE_NIGHT_NO -> setNavigationBarColor(colorLight)
//            Configuration.UI_MODE_NIGHT_YES -> setNavigationBarColor(colorDark)
//        }
//    }
}
