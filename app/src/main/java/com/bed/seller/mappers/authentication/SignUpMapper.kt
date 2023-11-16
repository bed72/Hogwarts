package com.bed.seller.mappers.authentication

import javax.inject.Inject

import com.bed.seller.mappers.Mapper

import com.bed.core.domain.parameters.authentication.SignUpParameter

import com.bed.seller.framework.network.request.authentication.SignUpRequest
import com.bed.seller.framework.network.request.authentication.SignUpDataRequest

class SignUpMapper @Inject constructor() : Mapper<SignUpParameter, SignUpRequest> {
    override fun invoke(parameter: SignUpParameter) = SignUpRequest(
        email = parameter.email(),
        password = parameter.password(),
        data = SignUpDataRequest(name = parameter.name())
    )
}
