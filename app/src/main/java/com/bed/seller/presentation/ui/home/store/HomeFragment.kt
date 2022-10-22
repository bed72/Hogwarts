package com.bed.seller.presentation.ui.home.store

import android.os.Bundle

import com.bed.seller.R
import com.bed.seller.databinding.HomeFragmentBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.bed.seller.presentation.ui.common.fragment.BaseFragment

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavigationBarColorTheme(R.color.navbar_dark, R.color.navbar_light)
    }
}
