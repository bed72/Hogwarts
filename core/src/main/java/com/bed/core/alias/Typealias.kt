package com.bed.core.alias

import arrow.core.Either

import kotlinx.coroutines.flow.Flow

import com.bed.core.entities.output.MessageOutput
import com.bed.core.entities.output.AuthenticationOutput

typealias AuthenticationCoreType = Flow<Either<MessageOutput, AuthenticationOutput>>
