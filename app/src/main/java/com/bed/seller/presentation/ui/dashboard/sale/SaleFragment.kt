package com.bed.seller.presentation.ui.dashboard.sale

import javax.inject.Inject

import android.util.Log
import android.view.View
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

import dagger.hilt.android.AndroidEntryPoint

import androidx.core.content.ContextCompat

import com.google.common.util.concurrent.ListenableFuture

import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

import com.bed.seller.databinding.SaleFragmentBinding
import com.bed.seller.presentation.ui.dashboard.home.model.HomeScreenModel
import com.bed.seller.presentation.ui.dashboard.home.viewholder.HomeViewHolder

import com.bed.seller.presentation.commons.loaders.ImageLoader
import com.bed.seller.presentation.commons.recyclers.getGenericAdapterOf
import com.bed.seller.presentation.commons.extensions.fragments.snackbar
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class SaleFragment : BaseBottomSheetDialogFragment<SaleFragmentBinding>(SaleFragmentBinding::inflate) {

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var cameraSelector: CameraSelector
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val adapterImages by lazy {
        getGenericAdapterOf { HomeViewHolder.create(it, imageLoader) }
    }

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
            if (it.isNotEmpty()) adapterImages.submitList(it.mapIndexed { id, image -> HomeScreenModel(id, image) })
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

    private fun setupComponents() {
        initAdapter()

        setupCancel()
//      camera.launch(android.Manifest.permission.CAMERA)
//      openGallery()
//      openGalleryLegacy()
    }

    private fun initAdapter() {
        binding.selectedImagesRecycler.run {
            setHasFixedSize(true)
            adapter = adapterImages
        }
    }

    private fun setupCancel() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

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
//                it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
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
