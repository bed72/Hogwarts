package com.bed.seller.data.client

import com.bed.seller.domain.alias.AuthEitherModelType

import com.bed.seller.domain.entities.paths.PathEntity

import com.bed.seller.domain.entities.auth.signup.SignUpBodyRequestEntity

interface AuthSignUpClient {
    suspend operator fun invoke(path: PathEntity, params: SignUpBodyRequestEntity): AuthEitherModelType
}
