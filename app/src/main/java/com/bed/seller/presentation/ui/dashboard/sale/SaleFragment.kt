package com.bed.seller.presentation.ui.dashboard.sale

import javax.inject.Inject

import java.io.File
import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat

import android.net.Uri
import android.Manifest
import android.util.Log
import android.view.View
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher

import dagger.hilt.android.AndroidEntryPoint

import androidx.core.content.FileProvider
import androidx.core.content.ContextCompat

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

import com.bed.seller.BuildConfig
import com.bed.seller.R

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

    private lateinit var bed: ActivityResultLauncher<Uri>

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

    private val getCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) {

    }

    private val getPermissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) getCamera.launch(createImageUri())
        else snackbar(binding.root, "The camera permission is required")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        initAdapter()

        setupSave()
        setupCancel()
    }

    private fun initAdapter() {
        binding.selectedImagesRecycler.run {
            setHasFixedSize(true)
            adapter = adapterImages
        }
    }

    private fun setupSave() {
        binding.saveButton.setOnClickListener {
            handlerCamera()
//      openGallery()
//      openGalleryLegacy()
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
        val permission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)

        if (permission == PackageManager.PERMISSION_GRANTED) getCamera.launch(createImageUri())
        else getPermissionCamera.launch(Manifest.permission.CAMERA)
    }

    private fun createImageUri(): Uri {
        val name = SimpleDateFormat(getString(R.string.pattern_date_images), Locale.US).run {
            format(Date())
        }
        val dir = File(requireContext().filesDir, getString(R.string.pattern_save_images)).apply {
            mkdir()
        }

        return FileProvider
            .getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.provider",
                File(dir,  "${name}.jpg")
            )
    }
}
