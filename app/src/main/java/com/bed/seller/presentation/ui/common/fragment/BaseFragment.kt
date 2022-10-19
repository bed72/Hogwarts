package com.bed.seller.presentation.ui.common.fragment

import android.os.Bundle

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

import android.content.res.Configuration

import androidx.viewbinding.ViewBinding

import com.bed.seller.R
import com.bed.seller.presentation.extensions.setNavigationBarColor

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<viewBinding : ViewBinding>(
    private val inflate: Inflate<viewBinding>
) : Fragment() {

    private var _binding: viewBinding? = null
    protected val binding: viewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater, container, false)

        return binding.root
    }

    protected fun setNavigationBarColorTheme(
        @ColorRes colorDark: Int = R.color.dark_background,
        @ColorRes colorLight: Int = R.color.light_background
    ) {
        when (requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> setNavigationBarColor(colorLight)
            Configuration.UI_MODE_NIGHT_YES -> setNavigationBarColor(colorDark)
        }
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
