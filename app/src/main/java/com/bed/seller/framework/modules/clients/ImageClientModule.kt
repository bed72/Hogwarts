package com.bed.seller.framework.modules.clients

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

import com.bed.seller.presentation.commons.loaders.ImageLoader
import com.bed.seller.presentation.commons.loaders.CoilImageLoader

@Module
@InstallIn(FragmentComponent::class)
interface ImageClientModule {
    @Binds
    fun bindImageLoader(loader: CoilImageLoader): ImageLoader
}
