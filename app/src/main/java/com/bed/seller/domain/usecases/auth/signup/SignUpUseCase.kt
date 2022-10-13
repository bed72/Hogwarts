package com.bed.seller.domain.usecases.auth.signup

import kotlinx.coroutines.flow.Flow

import com.bed.seller.domain.alias.AuthEitherEntityType
import com.bed.seller.domain.entities.auth.AuthRequestEntity

interface SignUpUseCase {
    operator fun invoke(params: AuthRequestEntity): Flow<AuthEitherEntityType>
}
