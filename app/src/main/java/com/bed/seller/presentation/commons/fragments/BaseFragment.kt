package com.bed.seller.presentation.commons.fragments

import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.viewbinding.ViewBinding

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.MenuItem
import android.view.ViewGroup
import android.view.MenuInflater
import android.view.LayoutInflater

typealias InflateFragment<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<viewBinding : ViewBinding>(
    private val inflate: InflateFragment<viewBinding>
) : Fragment(), MenuProvider {

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

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    @Suppress("EmptyFunctionBlock")
    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) { }

    override fun onMenuItemSelected(menu: MenuItem): Boolean = false

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
