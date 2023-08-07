package com.bed.seller.presentation.ui.dashboard.permission

import android.os.Build
import android.os.Bundle
import android.view.View

import androidx.annotation.RequiresApi

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.bed.seller.R

import com.bed.seller.databinding.PermissionFragmentBinding

import com.bed.seller.presentation.commons.extensions.setDivider
import com.bed.seller.presentation.commons.permissions.Permissions
import com.bed.seller.presentation.commons.recyclers.getGenericAdapterOf
import com.bed.seller.presentation.commons.extensions.fragments.snackbar
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment
import com.bed.seller.presentation.commons.extensions.fragments.shouldRequestPermission

import com.bed.seller.presentation.ui.dashboard.permission.model.PermissionScreenModel
import com.bed.seller.presentation.ui.dashboard.permission.viewholder.PermissionViewHolder

class PermissionFragment : BaseBottomSheetDialogFragment<PermissionFragmentBinding>(
    PermissionFragmentBinding::inflate
) {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private val adapterPermission by lazy {
        getGenericAdapterOf { PermissionViewHolder.create(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPermissions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setupComponents()
    }

    private fun setupComponents() {
        setupButtons()
        setupAdapter()
    }

    private fun initAdapter() {
        binding.permissionRecycler.run {
            setHasFixedSize(true)
            adapter = adapterPermission
            setDivider(R.drawable.divider)
        }
    }

    private fun setPermissions() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val grant = permissions.all { it.value }
            val identified = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                permissions.all { it.key in Permissions.permissionsCommons }
            } else permissions.all { it.key in Permissions.permissionsToTiramisu }


            if (grant and identified) snackbar(requireView(), getString(R.string.permissions_success))
            else snackbar(requireView(), getString(R.string.permissions_failure))

        }
    }

    private fun setupButtons() {
        with(binding) {
            permissionNoAcceptButton.setOnClickListener { dismiss() }
            permissionAcceptButton.setOnClickListener { setupPermissions() }
        }
    }

    private fun setupAdapter() {
        resources.getStringArray(R.array.permissions_request).mapIndexed { id, description ->
            PermissionScreenModel(id, description)
        }.run { adapterPermission.submitList(this) }
    }

    private fun setupPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            requestPermissions()
        } else {
            requestPermissionsTiramisu()
        }
    }

    private fun requestPermissions() {
        if (shouldRequestPermission(Permissions.permissionsCommons))
            permissionLauncher.launch(Permissions.permissionsCommons)
        else snackbar(requireView(), getString(R.string.permissions_failure))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissionsTiramisu() {
        if (shouldRequestPermission(Permissions.permissionsToTiramisu))
            permissionLauncher.launch(Permissions.permissionsToTiramisu)
        else snackbar(requireView(), getString(R.string.permissions_failure))
    }
}
