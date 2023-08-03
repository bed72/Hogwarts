package com.bed.seller.presentation.ui.dashboard.home

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.MenuItem
import android.view.MenuInflater

import androidx.lifecycle.Lifecycle
import androidx.activity.addCallback
import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint

import com.bed.seller.R

import com.bed.seller.databinding.HomeFragmentBinding

import com.bed.seller.presentation.commons.extensions.navigateTo
import com.bed.seller.presentation.commons.fragments.BaseFragment
import com.bed.seller.presentation.commons.permissions.Permissions
import com.bed.seller.presentation.commons.extensions.hasPermissions

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    override fun onResume() {
        super.onResume()

        setNavigationBarColorTheme(R.color.navbar_dark, R.color.navbar_light)
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_items, menu)
    }

    override fun onMenuItemSelected(menu: MenuItem): Boolean =
        when (menu.itemId) {
            R.id.settings -> {
                navigateTo(HomeFragmentDirections.actionHomeFragmentToSettingFragment())
                true
            }
            R.id.notification -> {
                navigateTo(HomeFragmentDirections.actionHomeFragmentToNotificationFragment())
                true
            }
            else -> false
        }

    private fun setupComponents() {
        setupMenu()
        setupExitApp()
        setupFloatActionBottom()
        setupHandlerPermissions()
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupFloatActionBottom() {
        binding.floatingActionButton.setOnClickListener {
            navigateTo(HomeFragmentDirections.actionHomeFragmentToSaleFragment())
        }
    }

    private fun setupHandlerPermissions() {
        if (hasPermissions().not())
            navigateTo(HomeFragmentDirections.actionHomeFragmentToPermissionFragment())
    }

    private fun setupExitApp() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateTo(HomeFragmentDirections.actionHomeFragmentToVerifyExitFragment())
        }
    }

    private fun hasPermissions() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
        hasPermissions(Permissions.permissionsCommons)
        else hasPermissions(Permissions.permissionsToTiramisu)

}
