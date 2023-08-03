package com.bed.core.domain.alias

import arrow.core.Either

import com.bed.core.domain.models.failure.MessageModel
import com.bed.core.domain.models.authentication.SignUpModel

typealias SignUpType = Either<MessageModel, SignUpModel>
