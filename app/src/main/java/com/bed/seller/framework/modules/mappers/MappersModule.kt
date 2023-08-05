package com.bed.seller.framework.modules.mappers

import javax.inject.Named

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.seller.mappers.Mapper
import com.bed.seller.mappers.authentication.SignUpMapper
import com.bed.core.domain.parameters.authentication.SignUpParameters
import com.bed.seller.framework.network.request.authentication.SignUpRequest

@Module
@InstallIn(SingletonComponent::class)
interface MappersModule {
    @Binds
    @Named("SignUpMapper")
    fun bindSignUpMapper(mapper: SignUpMapper): Mapper<SignUpParameters, SignUpRequest>
}
