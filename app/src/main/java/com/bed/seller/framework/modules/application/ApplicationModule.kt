package com.bed.seller.framework.modules.application

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

import com.bed.seller.presentation.commons.loaders.CoilImageLoader
import com.bed.seller.presentation.commons.loaders.ImageLoader

@Module
@InstallIn(FragmentComponent::class)
interface ApplicationModule {
    @Binds
    fun bindImageLoader(loader: CoilImageLoader): ImageLoader
}
