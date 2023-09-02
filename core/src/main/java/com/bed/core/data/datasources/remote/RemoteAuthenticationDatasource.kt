package com.bed.core.data.datasources.remote

import com.bed.core.domain.alias.AuthenticationType

import com.bed.core.domain.parameters.authentication.AuthenticationParameter

interface RemoteAuthenticationDatasource {
    suspend fun verify(): Boolean
    suspend fun signUp(parameter: AuthenticationParameter): AuthenticationType
    suspend fun signIn(parameter: AuthenticationParameter): AuthenticationType
}
