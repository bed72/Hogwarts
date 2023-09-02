package com.bed.seller.framework.modules.usecases

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import com.bed.core.usecases.authentication.VerifyUseCase
import com.bed.core.usecases.authentication.VerifyUseCaseImpl

import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.core.usecases.authentication.SignUpUseCaseImpl

import com.bed.core.usecases.authentication.SignInUseCase
import com.bed.core.usecases.authentication.SignInUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface AuthenticationUseCaseModule {
    @Binds
    fun bindVerify(useCase: VerifyUseCaseImpl): VerifyUseCase

    @Binds
    fun bindSignUpUseCase(useCase: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    fun bindSignInUseCase(useCase: SignInUseCaseImpl): SignInUseCase
}
