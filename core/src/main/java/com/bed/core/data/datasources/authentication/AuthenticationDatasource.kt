package com.bed.core.data.datasources.authentication

import com.bed.core.domain.alias.SignUpType

import com.bed.core.domain.parameters.authentication.SignUpParameters

interface AuthenticationDatasource {
    suspend fun signUp(parameters: SignUpParameters): SignUpType
}
