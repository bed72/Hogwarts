package com.bed.core.data.datasources.remote

import com.bed.core.domain.alias.SignUpType

import com.bed.core.domain.parameters.authentication.SignUpParameter

interface RemoteAuthenticationDatasource {
    suspend fun signUp(parameters: SignUpParameter): SignUpType
}
