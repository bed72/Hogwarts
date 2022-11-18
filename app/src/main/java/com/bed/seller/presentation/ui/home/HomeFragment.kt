package com.bed.seller.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs

import com.bed.seller.R
import com.bed.seller.databinding.HomeFragmentBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.bed.seller.presentation.ui.common.fragment.BaseFragment
import com.bed.seller.presentation.ui.account.AccountViewModel

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: AccountViewModel by viewModel()

    private val args by navArgs<HomeFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavigationBarColorTheme(R.color.navbar_dark, R.color.navbar_light)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = args.userName
        binding.title.text = userName
    }
}
