package com.bed.seller.presentation.ui.dashboard.home

import android.util.Log

import android.os.Build
import android.os.Bundle

import android.app.Activity
import android.content.Intent

import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater

import android.provider.MediaStore

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

import com.bed.seller.presentation.commons.fragments.BaseFragment

import com.bed.seller.presentation.commons.permissions.Permissions
import com.bed.seller.presentation.commons.extensions.fragments.snackbar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.hasPermissions

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

//    private val viewModel: HomeViewModel by viewModels()

    private lateinit var cameraSelector: CameraSelector
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val getPhotoFromGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) Log.d("Picker", "Photo: $it")
            else snackbar(binding.root, "No media selected")
        }

    private val getPhotoFromGalleryLegacy get() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) Log.d("Picker", "Photo: $it.data?.data")
            else snackbar(binding.root, "No media selected")
        }

    private val getPhotosFromGallery =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) {
            if (it.isNotEmpty()) Log.d("Picker", "Photos: $it")
            else snackbar(binding.root, "No media selected")
        }

    private val openCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
        if (permission) handlerCamera()
        else snackbar(binding.root, "The camera permission is required")
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
//            openGalleryLegacy()
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
        getPhotosFromGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
    }

    private fun openGalleryLegacy() {
        getPhotoFromGalleryLegacy
            .launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
    }

    private fun handlerCamera() {
        cameraProviderFuture.addListener({
            val provider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also{
                it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            }

            try {
                provider.unbindAll()
                provider.bindToLifecycle(this, cameraSelector, preview)
            } catch (exception: Exception) {
                snackbar(binding.root, "The camera is broke")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

}
