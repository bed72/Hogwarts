package com.bed.seller.presentation.ui.dashboard.gallery

import javax.inject.Inject

import android.net.Uri
import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.seller.presentation.commons.states.States

import com.bed.core.usecases.coroutines.CoroutinesUseCase
import kotlinx.coroutines.delay

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val application: Application,
    private val useCase: CoroutinesUseCase
) : ViewModel() {
    private val _counterImages: MutableStateFlow<Int> = MutableStateFlow(0)
    val counterImages: StateFlow<Int> get() = _counterImages.asStateFlow()

    private val _selectedImages: MutableStateFlow<MutableSet<Uri>> = MutableStateFlow(mutableSetOf())
    val selectedImages: StateFlow<Set<Uri>> get() = _selectedImages.asStateFlow()

    private val _imagesFromGallery: MutableStateFlow<States<List<Uri>>> = MutableStateFlow(States.Initial)
    val imagesFromGallery: StateFlow<States<List<Uri>>> get() = _imagesFromGallery.asStateFlow()

    fun getAllImagesFromGallery() {
        viewModelScope.launch(useCase.io()) {
            _imagesFromGallery.update { States.Loading }

            val uris = mutableListOf<Uri>()

            application.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID),
                null,
                null,
                "${MediaStore.Images.Media.DATE_ADDED} DESC"
            )?.use {cursor ->
                while (cursor.moveToNext()) {
                    uris.add(
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL),
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                        )
                    )
                }
            }

            if (uris.isNotEmpty()) _imagesFromGallery.update { States.Success(uris) }
            else _imagesFromGallery.update { States.Failure("Ops! não conseguimos carregar suas fotos.") }
        }
    }

    fun setSelectedImages(images: MutableSet<Uri>) {
        _selectedImages.update { images }
        _counterImages.update { images.size }
    }
}
