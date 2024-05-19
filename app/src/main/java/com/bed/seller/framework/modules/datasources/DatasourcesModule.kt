package com.bed.seller.framework.modules.datasources

import com.bed.seller.data.datasources.AuthenticationDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.seller.data.datasources.StorageDatasource

import com.bed.seller.framework.datasources.local.LocalStorageDatasourceImpl
import com.bed.seller.framework.datasources.remote.RemoteAuthenticationDatasourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface DatasourcesModule {
    @Binds
    fun bindStorageDatasource(datasource: LocalStorageDatasourceImpl): StorageDatasource

    @Binds
    fun bindAuthenticationDatasource(datasource: RemoteAuthenticationDatasourceImpl): AuthenticationDatasource
}
