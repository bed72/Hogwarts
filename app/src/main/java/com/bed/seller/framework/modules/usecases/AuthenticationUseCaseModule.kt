package com.bed.seller.framework.modules.usecases

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.core.usecases.authentication.SignUpUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface AuthenticationUseCaseModule {
    @Binds
    fun bindSignUpUseCase(useCase: SignUpUseCaseImpl): SignUpUseCase
}
