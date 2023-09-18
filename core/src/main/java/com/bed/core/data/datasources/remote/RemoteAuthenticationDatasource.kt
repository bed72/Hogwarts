package com.bed.core.data.datasources.remote

import com.bed.core.domain.alias.AuthenticationType

import com.bed.core.domain.parameters.authentication.RecoverParameter
import com.bed.core.domain.parameters.authentication.AuthenticationParameter
import com.bed.core.domain.parameters.authentication.ResetParameter

interface RemoteAuthenticationDatasource {
    fun signOut(): Boolean
    fun isLoggedIn(): Boolean
    suspend fun reset(parameter: ResetParameter): Boolean
    suspend fun recover(parameter: RecoverParameter): Boolean
    suspend fun signUp(parameter: AuthenticationParameter): AuthenticationType
    suspend fun signIn(parameter: AuthenticationParameter): AuthenticationType
}
