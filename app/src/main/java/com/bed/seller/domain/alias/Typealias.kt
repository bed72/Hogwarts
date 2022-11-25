package com.bed.seller.domain.alias

import arrow.core.Either

import com.bed.seller.domain.entities.ResponseEntity
import com.bed.seller.domain.entities.auth.AuthResponseEntity
import com.bed.seller.domain.entities.auth.user.UserResponseEntity
import com.bed.seller.domain.entities.failure.MessageFailureResponseEntity

import com.bed.seller.infrastructure.network.models.ResponseModel
import com.bed.seller.infrastructure.network.models.auth.AuthResponseModel
import com.bed.seller.infrastructure.network.models.auth.user.UserResponseModel
import com.bed.seller.infrastructure.network.models.failure.MessageFailureResponseModel

/**
 * @AuthEitherModelType
 * @AuthEitherEntityType
 *
 * Refer to SignUp and SignIn flux
 */
typealias AuthEitherModelType =
        Either<ResponseModel<MessageFailureResponseModel>, ResponseModel<AuthResponseModel>>

typealias AuthEitherEntityType =
        Either<ResponseEntity<MessageFailureResponseEntity>, ResponseEntity<AuthResponseEntity>>

/**
 * @UserEitherModelType
 * @UserEitherEntityType
 *
 * Refer to User flux
 */
typealias UserEitherModelType =
        Either<ResponseModel<MessageFailureResponseModel>, ResponseModel<UserResponseModel>>

typealias UserEitherEntityType =
        Either<ResponseEntity<MessageFailureResponseEntity>, ResponseEntity<UserResponseEntity>>
