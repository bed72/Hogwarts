package com.bed.seller.framework.modules.adapters

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import javax.inject.Singleton

import android.content.Context

import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

import com.bed.seller.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object StorageClientConfigurationModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        preferencesDataStore(name = BuildConfig.DATA_STORE).getValue(context, String::javaClass)
}
