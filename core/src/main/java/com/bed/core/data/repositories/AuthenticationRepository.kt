package com.bed.core.data.repositories

import javax.inject.Inject

import com.bed.core.domain.parameters.authentication.ResetParameter
import com.bed.core.domain.parameters.authentication.RecoverParameter

import com.bed.core.data.datasources.remote.RemoteAuthenticationDatasource

import com.bed.core.domain.alias.AuthenticationType
import com.bed.core.domain.parameters.authentication.AuthenticationParameter

interface AuthenticationRepository {
    fun signOut()
    fun isLoggedIn(): Boolean
    suspend fun reset(parameter: ResetParameter): Boolean
    suspend fun recover(parameter: RecoverParameter): Boolean
    suspend fun signUp(parameter: AuthenticationParameter): AuthenticationType
    suspend fun signIn(parameter: AuthenticationParameter): AuthenticationType
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val datasource: RemoteAuthenticationDatasource
) : AuthenticationRepository {
    override fun signOut(): Unit = datasource.signOut()

    override fun isLoggedIn(): Boolean = datasource.isLoggedIn()

    override suspend fun reset(parameter: ResetParameter): Boolean = datasource.reset(parameter)

    override suspend fun recover(parameter: RecoverParameter): Boolean = datasource.recover(parameter)

    override suspend fun signUp(parameter: AuthenticationParameter): AuthenticationType =
        datasource.signUp(parameter)

    override suspend fun signIn(parameter: AuthenticationParameter): AuthenticationType =
        datasource.signIn(parameter)
}
