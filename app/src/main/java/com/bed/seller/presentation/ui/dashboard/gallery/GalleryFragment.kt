package com.bed.seller.presentation.ui.dashboard.gallery

import dagger.hilt.android.AndroidEntryPoint

import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.activity.result.contract.ActivityResultContracts

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED

import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.bed.seller.R

import com.bed.seller.databinding.GalleryFragmentBinding

import com.bed.seller.presentation.commons.states.States
import com.bed.seller.presentation.commons.states.ConstantStates
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.extensions.fragments.navigateTo
import com.bed.seller.presentation.commons.extensions.fragments.lifecycleExecute
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

import com.bed.seller.presentation.ui.dashboard.gallery.adapter.GalleryAdapter
import com.bed.seller.presentation.ui.dashboard.gallery.model.GalleryScreenModel
import com.bed.seller.presentation.ui.dashboard.gallery.model.FromCameraScreenModel
import com.bed.seller.presentation.ui.dashboard.gallery.model.FromGalleryScreenModel

@AndroidEntryPoint
class GalleryFragment : BaseBottomSheetDialogFragment<GalleryFragmentBinding>(GalleryFragmentBinding::inflate) {

    private val viewModel: GalleryViewModel by viewModels()

    //    private var image: Uri = Uri.EMPTY

//    private val getPhotoFromGallery =
//        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
//            if (it != null) Log.d("Picker", "Photo: $it")
//            else snackBar(binding.root, "No media selected")
//        }


//    private val getPhotosFromGallery =
//        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) {
//            if (it.isNotEmpty()) adapterImages.submitList(it.mapIndexed { id, image -> ImageOfferScreenModel(id, image) })
//            else snackBar(binding.root, "No media selected")
//        }

//    private val getCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) {
//        if (it) adapterImages.submitList(listOf(image).mapIndexed { id, image ->
//            ImageOfferScreenModel(id, image)
//        })
//    }

//    private val getPermissionCamera =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//        if (it) getCamera.launch(createImageUri())
//        else snackBar(binding.root, "The camera permission is required")
//    }

    private val getPermissionGallery =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            handlerImagesFromGallery(it)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()

        observeSelectedImagesState()
        observeImagesFromGalleryState()
    }

    private fun observeSelectedImagesState() {
        lifecycleExecute { viewModel.counterImages.collect { handlerSelectedButton(it) } }
    }

    private fun observeImagesFromGalleryState() {
        lifecycleExecute {
            viewModel.imagesFromGallery.collect { state ->
                binding.actionFlipper.displayedChild = when (state) {
                    States.Initial, States.Loading -> ConstantStates.FLIPPER_LOADING
                    is States.Failure -> setFailure(state.data)
                    is States.Success -> setSuccess(state.data)
                }
            }
        }
    }

    private fun setupComponents() {
        setupButtons()
        setupFullBottomSheet()

        handlerPermissions()
    }

    private fun setFailure(data: String): Int {
        binding.failureText.text = data

        return ConstantStates.FLIPPER_FAILURE
    }

    private fun setSuccess(data: List<Uri>): Int {
        if (data.isEmpty()) return setFailure(getString(R.string.generic_failure_title))

        setupRecycler(data)

        return ConstantStates.FLIPPER_SUCCESS
    }

    private fun setupRecycler(data: List<Uri>) {
        binding.imagesRecycler.run {
            adapter = GalleryAdapter(handlerUrls(data))

        }
    }

    private fun setupButtons() {
        with (binding) {
            cancelButton.setOnClickListener { dismiss() }
            selectButton.setOnClickListener {  }
        }
    }

    private fun setupFullBottomSheet() {
        val bottom = dialog?.findViewById<FrameLayout>(BOTTOM_SHEET)

        BottomSheetBehavior.from(bottom!!).apply {
            peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        }
    }

    private fun handlerImagesFromGallery(permissions: Map<String, @JvmSuppressWildcards Boolean>) {
        val isAllowed = permissions.values.all { it }

        if (isAllowed) viewModel.getAllImagesFromGallery() else snackBar(R.string.gallery_permissions_message)
    }

    private fun handlerUrls(urls: List<Uri>): List<GalleryScreenModel> =
        urls.mapIndexed { index, url ->
            if (index == 0) FromCameraScreenModel { navigateToCamera() }
            else FromGalleryScreenModel(url) { viewModel.setSelectedImages(it) }
        }

    private fun handlerPermissions() {
        when (Build.VERSION.SDK_INT) {
            Build.VERSION_CODES.TIRAMISU -> getPermissionGallery.launch(TIRAMISU)
            Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> getPermissionGallery.launch(CAKE)
            else -> getPermissionGallery.launch(OTHERS)
        }
    }

    private fun handlerSelectedButton(quantity: Int) {
        with (binding.selectButton) {
            text = when (quantity) {
                0 -> getText(R.string.gallery_title_save_button)
                1 -> getString(R.string.gallery_selected_title_save_button, quantity.toString())
                in 2..5 -> getString(R.string.gallery_multiple_selected_title_save_button, quantity.toString())
                else -> getString(R.string.gallery_max_selected_message)
            }

            isEnabled = quantity < 6
        }
    }

    private fun navigateToCamera() {
        navigateTo(GalleryFragmentDirections.actionGalleryToCamera())
    }

//    private fun openGallery() {
//        getPhotosFromGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }

//    private fun handlerCamera() {
//        val permission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
//
//        if (permission == PackageManager.PERMISSION_GRANTED) getCamera.launch(createImageUri())
//        else getPermissionCamera.launch(Manifest.permission.CAMERA)
//    }



//    private fun createImageUri(): Uri {
//        val name = SimpleDateFormat(getString(R.string.pattern_date_images), Locale.US).run { format(Date()) }
//        val directory = File(requireContext().filesDir, getString(R.string.pattern_save_images)).apply { mkdir() }
//        return FileProvider.getUriForFile(
//                requireContext(),
//                "${BuildConfig.APPLICATION_ID}.provider",
//                File(directory, "${name}.jpg")
//            ).apply { image = this }
//    }

//    private fun clearCache(){
//        requireContext().cacheDir.deleteRecursively()
//    }
//
//    private fun loadBitmapFromUri(uri: Uri): Bitmap {
//        val src = ImageDecoder.createSource(requireContext().contentResolver, uri)
//        return ImageDecoder.decodeBitmap(src) { decoder, _, _ ->
//            decoder.isMutableRequired = true
//        }
//    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private val TIRAMISU = arrayOf(READ_MEDIA_IMAGES)
        private val OTHERS = arrayOf(READ_EXTERNAL_STORAGE)
        @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
        private val CAKE = arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VISUAL_USER_SELECTED)
        private val BOTTOM_SHEET = com.google.android.material.R.id.design_bottom_sheet
    }
}
