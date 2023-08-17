package com.bed.seller.framework.modules.datasources

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.core.data.datasources.local.LocalStorageDataSource
import com.bed.seller.datasources.local.LocalStorageDataSourceImpl

import com.bed.core.data.datasources.remote.RemoteAuthenticationDatasource
import com.bed.seller.datasources.remote.RemoteAuthenticationDatasourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface DatasourcesModule {
    @Binds
    fun bindStorageDatasource(datasource: LocalStorageDataSourceImpl): LocalStorageDataSource

    @Binds
    fun bindAuthenticationDatasource(
        datasource: RemoteAuthenticationDatasourceImpl
    ): RemoteAuthenticationDatasource
}
