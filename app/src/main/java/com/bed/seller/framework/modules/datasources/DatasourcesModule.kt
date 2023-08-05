package com.bed.seller.framework.modules.datasources

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.core.data.datasources.storage.StorageDataSource
import com.bed.core.data.datasources.authentication.AuthenticationDatasource

import com.bed.seller.datasources.local.storage.LocalStorageDataSourceImpl
import com.bed.seller.datasources.remote.authentication.RemoteAuthenticationDatasourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface DatasourcesModule {
    @Binds
    fun bindStorageDatasource(datasource: LocalStorageDataSourceImpl): StorageDataSource

    @Binds
    fun bindAuthenticationDatasource(datasource: RemoteAuthenticationDatasourceImpl): AuthenticationDatasource
}
