package com.bed.core.data.repositories

import javax.inject.Inject

import com.bed.core.data.datasources.remote.RemoteAuthenticationDatasource

import com.bed.core.domain.alias.AuthenticationType
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

interface AuthenticationRepository {
    suspend fun verify(): Boolean
    suspend fun signUp(parameter: AuthenticationParameter): AuthenticationType
    suspend fun signIn(parameter: AuthenticationParameter): AuthenticationType
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val datasource: RemoteAuthenticationDatasource
) : AuthenticationRepository {
    override suspend fun verify(): Boolean = datasource.verify()

    override suspend fun signUp(parameter: AuthenticationParameter): AuthenticationType =
        datasource.signUp(parameter)

    override suspend fun signIn(parameter: AuthenticationParameter): AuthenticationType =
        datasource.signIn(parameter)
}
