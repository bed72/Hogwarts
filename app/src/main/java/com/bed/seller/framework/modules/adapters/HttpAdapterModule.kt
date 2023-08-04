package com.bed.seller.framework.modules.adapters

import com.bed.seller.framework.network.adapters.HttpAdapter
import com.bed.seller.framework.network.adapters.HttpAdapterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HttpAdapterModule {
    @Binds
    @Singleton
    fun bindHttpAdapter(adapter: HttpAdapterImpl): HttpAdapter
}
