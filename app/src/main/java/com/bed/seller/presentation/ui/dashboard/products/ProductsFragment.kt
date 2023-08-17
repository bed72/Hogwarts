package com.bed.seller.presentation.ui.dashboard.products

import android.os.Bundle
import android.view.View

import androidx.activity.addCallback

import com.bed.seller.R

import com.bed.seller.databinding.ProductsFragmentBinding

import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.extensions.fragments.navigateTo

class ProductsFragment : BaseFragment<ProductsFragmentBinding>(ProductsFragmentBinding::inflate) {

//    private val viewModel: ProductsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    override fun onResume() {
        super.onResume()

        setNavigationBarColorTheme(R.color.navbar_dark, R.color.navbar_light)
    }

    private fun setupComponents() {
        setupExitApp()
    }

    private fun setupExitApp() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateTo(ProductsFragmentDirections.actionProductsToExit())
        }
    }
}
