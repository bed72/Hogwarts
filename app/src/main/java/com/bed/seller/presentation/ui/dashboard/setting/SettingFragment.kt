package com.bed.seller.presentation.ui.dashboard.setting

import android.os.Bundle
import android.view.View

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.SettingFragmentBinding

import com.bed.seller.presentation.commons.fragments.BaseFragment
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo

@AndroidEntryPoint
class SettingFragment : BaseFragment<SettingFragmentBinding>(SettingFragmentBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        setupLogoutButton()
    }

    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            navigateTo(R.id.action_setting_to_logout)
        }
    }
}
