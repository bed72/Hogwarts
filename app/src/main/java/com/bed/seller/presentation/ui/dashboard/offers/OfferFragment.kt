package com.bed.seller.presentation.ui.dashboard.offers

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
import android.graphics.Bitmap
import android.provider.MediaStore
import android.graphics.ImageDecoder
import android.content.pm.PackageManager

import dagger.hilt.android.AndroidEntryPoint

import androidx.core.content.FileProvider
import androidx.core.content.ContextCompat

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

import com.bed.seller.R
import com.bed.seller.BuildConfig

import com.bed.seller.databinding.OfferFragmentBinding

import com.bed.seller.presentation.commons.loaders.ImageLoader
import com.bed.seller.presentation.commons.recyclers.getGenericAdapterOf
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

import com.bed.seller.presentation.ui.dashboard.offers.model.ImageOfferScreenModel
import com.bed.seller.presentation.ui.dashboard.offers.viewholder.ImageOfferViewHolder

@AndroidEntryPoint
class OfferFragment : BaseBottomSheetDialogFragment<OfferFragmentBinding>(OfferFragmentBinding::inflate) {

    @Inject
    lateinit var imageLoader: ImageLoader

    private var image: Uri = Uri.EMPTY

//    private val viewModel: SaleViewModel by viewModels()

    private val adapterImages by lazy {
        getGenericAdapterOf { ImageOfferViewHolder.create(it, imageLoader) }
    }

    private val getPhotoFromGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) Log.d("Picker", "Photo: $it")
            else snackBar(binding.root, "No media selected")
        }

    private val getPhotoFromGalleryLegacy get() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) Log.d("Picker", "Photo: $it.data?.data")
            else snackBar(binding.root, "No media selected")
        }

    private val getPhotosFromGallery =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) {
            if (it.isNotEmpty()) adapterImages.submitList(it.mapIndexed { id, image -> ImageOfferScreenModel(id, image) })
            else snackBar(binding.root, "No media selected")
        }

    private val getCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) adapterImages.submitList(listOf(image).mapIndexed { id, image ->
            ImageOfferScreenModel(id, image)
        })
    }

    private val getPermissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) getCamera.launch(createImageUri())
        else snackBar(binding.root, "The camera permission is required")
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
//            openGallery()
        }
    }

    private fun openGallery() {
        getPhotosFromGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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
        val name = SimpleDateFormat(getString(R.string.pattern_date_images), Locale.US).run { format(Date()) }
        val directory = File(requireContext().filesDir, getString(R.string.pattern_save_images)).apply { mkdir() }
        return FileProvider.getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.provider",
                File(directory, "${name}.jpg")
            ).apply { image = this }
    }


    private fun clearCache(){
        requireContext().cacheDir.deleteRecursively()
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap {
        val src = ImageDecoder.createSource(requireContext().contentResolver, uri)
        return ImageDecoder.decodeBitmap(src) { decoder, _, _ ->
            decoder.isMutableRequired = true
        }
    }
}
