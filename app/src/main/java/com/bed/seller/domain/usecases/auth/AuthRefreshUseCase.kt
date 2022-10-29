package com.bed.seller.domain.usecases.auth

import kotlinx.coroutines.flow.Flow

import com.bed.seller.domain.alias.AuthEitherEntityType

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.tokens.RefreshTokenBodyRequestEntity

interface AuthRefreshUseCase {
    operator fun invoke(params: Params): Flow<AuthEitherEntityType>

    data class Params(val path: PathEntity, val body: RefreshTokenBodyRequestEntity)
}
