package com.bed.seller.data.client.auth

import com.bed.seller.data.alias.AuthEitherModelType

import com.bed.seller.domain.entities.paths.PathEntity
import com.bed.seller.domain.entities.auth.signup.SignUpBodyRequestEntity

interface SignUpClient {
    suspend operator fun invoke(path: PathEntity, params: SignUpBodyRequestEntity): AuthEitherModelType
}
