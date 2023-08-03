package com.bed.core.data.repositories.authentication

import javax.inject.Inject

import com.bed.core.domain.alias.SignUpType
import com.bed.core.domain.parameters.authentication.SignUpParameters

import com.bed.core.data.datasources.authentication.AuthenticationDatasource

interface AuthenticationRepository {
    suspend fun signUp(parameters: SignUpParameters): SignUpType
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val datasource: AuthenticationDatasource
) : AuthenticationRepository {
    override suspend fun signUp(parameters: SignUpParameters): SignUpType =
        datasource.signUp(parameters)
}
