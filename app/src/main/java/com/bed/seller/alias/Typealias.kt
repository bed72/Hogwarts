package com.bed.seller.alias

import arrow.core.Either

import kotlinx.coroutines.flow.Flow

import com.bed.seller.framework.network.response.MessageResponse
import com.bed.seller.framework.network.response.AuthenticationResponse

typealias AuthenticationAppType = Flow<Either<MessageResponse, AuthenticationResponse>>
