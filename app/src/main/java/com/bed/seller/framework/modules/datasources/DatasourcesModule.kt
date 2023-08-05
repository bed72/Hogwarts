package com.bed.seller.framework.modules.datasources

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.core.data.datasources.local.storage.LocalStorageDataSource
import com.bed.core.data.datasources.remote.authentication.RemoteAuthenticationDatasource

import com.bed.seller.datasources.local.storage.LocalLocalStorageDataSourceImpl
import com.bed.seller.datasources.remote.authentication.RemoteAuthenticationDatasourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface DatasourcesModule {
    @Binds
    fun bindStorageDatasource(datasource: LocalLocalStorageDataSourceImpl): LocalStorageDataSource

    @Binds
    fun bindAuthenticationDatasource(datasource: RemoteAuthenticationDatasourceImpl): RemoteAuthenticationDatasource
}
