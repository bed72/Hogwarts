package com.bed.seller.mappers.authentication

import javax.inject.Inject

import com.bed.core.domain.parameters.authentication.SignInParameter

import com.bed.seller.mappers.Mapper
import com.bed.seller.framework.network.request.authentication.SignInRequest

class SignInMapper @Inject constructor() : Mapper<SignInParameter, SignInRequest> {
    override fun invoke(parameter: SignInParameter) = SignInRequest(
        email = parameter.email(),
        password = parameter.password(),
    )
}
