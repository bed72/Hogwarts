package com.bed.core.data.repositories

import javax.inject.Inject

import com.bed.core.domain.alias.AuthenticationType
import com.bed.core.domain.parameters.authentication.SignUpParameter
import com.bed.core.domain.parameters.authentication.SignInParameter

import com.bed.core.data.datasources.remote.RemoteAuthenticationDatasource

interface AuthenticationRepository {
    suspend fun signUp(parameter: SignUpParameter): AuthenticationType
    suspend fun signIn(parameter: SignInParameter): AuthenticationType
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val datasource: RemoteAuthenticationDatasource
) : AuthenticationRepository {
    override suspend fun signUp(parameter: SignUpParameter): AuthenticationType = datasource.signUp(parameter)

    override suspend fun signIn(parameter: SignInParameter): AuthenticationType = datasource.signIn(parameter)
}
