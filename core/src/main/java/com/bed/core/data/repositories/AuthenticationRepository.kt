package com.bed.core.data.repositories

import javax.inject.Inject

import com.bed.core.domain.alias.SignUpType
import com.bed.core.domain.parameters.authentication.SignUpParameter

import com.bed.core.data.datasources.remote.RemoteAuthenticationDatasource

interface AuthenticationRepository {
    suspend fun signUp(parameters: SignUpParameter): SignUpType
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val datasource: RemoteAuthenticationDatasource
) : AuthenticationRepository {
    override suspend fun signUp(parameters: SignUpParameter): SignUpType =
        datasource.signUp(parameters)
}
