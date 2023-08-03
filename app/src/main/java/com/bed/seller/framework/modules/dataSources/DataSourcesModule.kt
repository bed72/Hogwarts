package com.bed.seller.framework.modules.dataSources

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.core.data.datasources.storage.StorageDataSource
import com.bed.seller.datasources.local.storage.LocalStorageDataSourceImpl

import com.bed.core.data.datasources.authentication.AuthenticationDatasource
import com.bed.seller.datasources.remote.authentication.RemoteAuthenticationDatasourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataSourcesModule {
    @Binds
    fun bindStorageDataSource(dataSource: LocalStorageDataSourceImpl): StorageDataSource

    @Binds
    fun bindAuthenticationDataSource(dataSource: RemoteAuthenticationDatasourceImpl): AuthenticationDatasource
}
