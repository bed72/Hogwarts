package com.bed.seller.presentation.ui.dashboard.products

import android.os.Bundle
import android.view.View

import androidx.activity.addCallback

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.ProductsFragmentBinding

import com.bed.seller.presentation.commons.extensions.navigateTo
import com.bed.seller.presentation.commons.fragments.BaseFragment

@AndroidEntryPoint
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