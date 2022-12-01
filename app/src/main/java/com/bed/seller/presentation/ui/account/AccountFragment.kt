package com.bed.seller.presentation.ui.account

import android.os.Bundle

import org.koin.androidx.viewmodel.ext.android.viewModel

import com.bed.seller.R
import com.bed.seller.databinding.AccountFragmentBinding

import com.bed.seller.presentation.ui.common.fragment.BaseFragment

class AccountFragment : BaseFragment<AccountFragmentBinding>(AccountFragmentBinding::inflate) {

    private val viewModel: AccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavigationBarColorTheme(R.color.navbar_dark, R.color.navbar_light)
    }
}
