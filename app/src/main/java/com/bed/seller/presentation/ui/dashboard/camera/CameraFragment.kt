package com.bed.seller.presentation.ui.dashboard.camera

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.camera.core.Preview
import androidx.core.content.ContextCompat
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider

import dagger.hilt.android.AndroidEntryPoint

import com.google.common.util.concurrent.ListenableFuture
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.bed.seller.R

import com.bed.seller.databinding.CameraFragmentBinding

import com.bed.seller.presentation.commons.constants.AppConstants
import com.bed.seller.presentation.commons.extensions.fragments.snackBar
import com.bed.seller.presentation.commons.fragments.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class CameraFragment : BaseBottomSheetDialogFragment<CameraFragmentBinding>(CameraFragmentBinding::inflate) {

    private lateinit var camera: ListenableFuture<ProcessCameraProvider>;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCamera()
        setupFullBottomSheet()
    }

    override fun onDestroy() {
        super.onDestroy()

        camera.cancel(true)
    }

    private fun setupFullBottomSheet() {
        val bottom = dialog?.findViewById<FrameLayout>(AppConstants.BOTTOM_SHEET)?.apply {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        BottomSheetBehavior.from(bottom!!).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            peekHeight = resources.displayMetrics.heightPixels
        }
    }

    private fun setupCamera() {
        camera = ProcessCameraProvider.getInstance(requireContext())
        camera.addListener({
            val cameraProvider: ProcessCameraProvider = camera.get()
            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(binding.camera.surfaceProvider) }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview)
            } catch(_: Exception) { snackBar(R.string.generic_failure_title) }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

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

}
