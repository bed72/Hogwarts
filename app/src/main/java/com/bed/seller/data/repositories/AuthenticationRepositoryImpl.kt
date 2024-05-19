package com.bed.seller.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

import com.bed.core.alias.AuthenticationCoreType

import com.bed.core.entities.input.ResetInput
import com.bed.core.entities.input.RecoverInput
import com.bed.core.entities.input.AuthenticationInput

import com.bed.core.repositories.AuthenticationRepository

import com.bed.seller.framework.network.response.toEntity

import com.bed.seller.data.datasources.AuthenticationDatasource

class AuthenticationRepositoryImpl @Inject constructor(
    private val datasource: AuthenticationDatasource
) : AuthenticationRepository {
    override fun signOut(): Unit = datasource.signOut()

    override fun isLoggedIn(): Boolean = datasource.isLoggedIn()

    override suspend fun reset(parameter: ResetInput): Flow<Boolean> =
        flowOf(datasource.reset(parameter))

    override suspend fun recover(parameter: RecoverInput): Flow<Boolean> =
        flowOf(datasource.recover(parameter))

    override suspend fun signUp(parameter: AuthenticationInput): AuthenticationCoreType =
        datasource.signUp(parameter)
            .take(1)
            .map { response ->
                response
                    .map { it.toEntity() }
                    .mapLeft { it.toEntity() }
            }

    override suspend fun signIn(parameter: AuthenticationInput): AuthenticationCoreType =
        datasource.signIn(parameter)
            .take(1)
            .map { response ->
                response
                    .map { it.toEntity() }
                    .mapLeft { it.toEntity() }
            }
}
