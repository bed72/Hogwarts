package com.bed.core.data.datasources.remote

import com.bed.core.domain.alias.AuthenticationType

import com.bed.core.domain.parameters.authentication.SignInParameter
import com.bed.core.domain.parameters.authentication.SignUpParameter

interface RemoteAuthenticationDatasource {
    suspend fun signUp(parameter: SignUpParameter): AuthenticationType
    suspend fun signIn(parameter: SignInParameter): AuthenticationType
}
