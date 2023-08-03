package com.bed.core.domain.alias

import arrow.core.Either

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.AuthenticationModel

typealias SignUpType = Either<MessageModel, AuthenticationModel>
