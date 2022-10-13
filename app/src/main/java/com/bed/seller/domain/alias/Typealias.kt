package com.bed.seller.domain.alias

import arrow.core.Either

import com.bed.seller.domain.entities.ResponseEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.failure.MessageFailureResponseEntity

import com.bed.seller.infrastructure.network.models.responses.ResponseModel
import com.bed.seller.infrastructure.network.models.responses.auth.AuthResponseModel
import com.bed.seller.infrastructure.network.models.responses.failure.MessageFailureResponseModel

typealias AuthEitherModelType =
        Either<ResponseModel<MessageFailureResponseModel>, ResponseModel<AuthResponseModel>>

typealias AuthEitherEntityType =
        Either<ResponseEntity<MessageFailureResponseEntity>, ResponseEntity<AuthResponseEntity>>
