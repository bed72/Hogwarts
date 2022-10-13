package com.bed.seller.data.client

import com.bed.seller.domain.alias.AuthEitherModelType
import com.bed.seller.domain.entities.auth.AuthRequestEntity

interface SignUpClient {
    suspend fun signUp(params: AuthRequestEntity): AuthEitherModelType
}
