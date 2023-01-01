package com.bed.seller.domain.usecases.auth

import kotlinx.coroutines.flow.Flow

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.data.alias.UserEitherEntityType

interface UserUseCase {
    operator fun invoke(params: Params): Flow<UserEitherEntityType>

    data class Params(val path: PathEntity)
}
