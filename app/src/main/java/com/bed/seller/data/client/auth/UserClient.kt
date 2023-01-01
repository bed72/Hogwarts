package com.bed.seller.data.client.auth

import com.bed.seller.data.alias.UserEitherModelType
import com.bed.seller.domain.entities.paths.PathEntity

interface UserClient {
    suspend operator fun invoke(path: PathEntity): UserEitherModelType
}
