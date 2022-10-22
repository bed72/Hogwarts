package com.bed.seller.data.client

import com.bed.seller.domain.alias.AuthEitherModelType

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.AuthBodyRequestEntity

interface AuthClient {
    suspend operator fun invoke(path: PathEntity, params: AuthBodyRequestEntity): AuthEitherModelType
}
