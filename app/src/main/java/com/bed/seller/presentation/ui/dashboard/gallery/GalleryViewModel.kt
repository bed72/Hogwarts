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

import com.bed.core.usecases.coroutines.CoroutinesUseCase

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val application: Application,
    private val useCase: CoroutinesUseCase
) : ViewModel() {
    private val _images: MutableStateFlow<List<Uri>> = MutableStateFlow(listOf())
    val images: StateFlow<List<Uri>> get() = _images.asStateFlow()

    fun getAllImagesFromGallery() {
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

        viewModelScope.launch(useCase.io()) { _images.update { uris } }
    }
}
