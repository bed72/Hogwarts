package com.bed.seller.framework.modules.clients

import android.content.Context

import coil.ImageLoader

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object ImageClientModule {
    @Provides
    fun provideImageClient(@ApplicationContext context: Context) = ImageLoader(context)
}
