package com.bed.core.data.datasources.remote.authentication

import com.bed.core.domain.alias.SignUpType

import com.bed.core.domain.parameters.authentication.SignUpParameters

interface RemoteAuthenticationDatasource {
    suspend fun signUp(parameters: SignUpParameters): SignUpType
}
