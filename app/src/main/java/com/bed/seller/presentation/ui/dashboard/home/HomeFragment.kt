package com.bed.seller.presentation.ui.dashboard.home

import android.os.Build
import android.os.Bundle

import android.util.Log

import android.app.Activity

import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater

import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture

import androidx.lifecycle.Lifecycle
import androidx.core.content.ContextCompat

import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider

import androidx.activity.addCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

import com.bed.seller.R
import com.bed.seller.databinding.HomeFragmentBinding

import com.bed.seller.presentation.commons.extensions.navigateTo
import com.bed.seller.presentation.commons.fragments.BaseFragment
import com.bed.seller.presentation.commons.permissions.Permissions
import com.bed.seller.presentation.commons.extensions.hasPermissions

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

//    private val viewModel: HomeViewModel by viewModels()

    private lateinit var cameraSelector: CameraSelector
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val multiple =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
            if (uris.isNotEmpty()) Log.d("PhotoPicker", "Number of items selected: $uris")
            else Log.d("PhotoPicker", "No media selected")
        }

    private val gallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val image = it.data?.data
                Log.d("PhotoPicker", "Number of items selected: $image")
            }
        }

    private val camera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
        if (permission) openCamera()
        else Snackbar
            .make(binding.root,"The camera permission is required", Snackbar.LENGTH_INDEFINITE)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

    }

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
                navigateTo(HomeFragmentDirections.actionHomeToSetting())
                true
            }
            R.id.notification -> {
                navigateTo(HomeFragmentDirections.actionHomeToNotification())
                true
            }
            else -> false
        }

    private fun setupComponents() {
        setupMenu()
        setupExitApp()
        setupFloatActionBottom()
//        setupHandlerPermissions()
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupFloatActionBottom() {
        binding.floatingActionButton.setOnClickListener {
//            camera.launch(android.Manifest.permission.CAMERA)
            openGallery()
//            navigateTo(HomeFragmentDirections.actionHomeToSale())
        }
    }

    private fun setupHandlerPermissions() {
        if (hasPermissions().not()) {
            navigateTo(HomeFragmentDirections.actionHomeToPermission())
        }
    }

    private fun setupExitApp() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateTo(HomeFragmentDirections.actionHomeToExit())
        }
    }

    private fun hasPermissions() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
        hasPermissions(Permissions.permissionsCommons)
    else hasPermissions(Permissions.permissionsToTiramisu)

    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//        gallery.launch(intent)
        multiple.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

    }

    private fun openCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also{
                it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            }

            try{
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (exception: Exception) {
                Log.d("TAG", "Use case binding failed")
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

}
