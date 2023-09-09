package com.bed.seller.framework.modules.usecases

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import com.bed.core.usecases.authentication.ResetUseCase
import com.bed.core.usecases.authentication.ResetUseCaseImpl

import com.bed.core.usecases.authentication.SignUpUseCase
import com.bed.core.usecases.authentication.SignUpUseCaseImpl

import com.bed.core.usecases.authentication.SignInUseCase
import com.bed.core.usecases.authentication.SignInUseCaseImpl

import com.bed.core.usecases.authentication.RecoverUseCase
import com.bed.core.usecases.authentication.RecoverUseCaseImpl

import com.bed.core.usecases.authentication.IsLoggedInUseCase
import com.bed.core.usecases.authentication.IsLoggedInUseCaseImpl


@Module
@InstallIn(ViewModelComponent::class)
interface AuthenticationUseCaseModule {
    @Binds
    fun bindIsLoggedIn(useCase: IsLoggedInUseCaseImpl): IsLoggedInUseCase

    @Binds
    fun binReset(useCase: ResetUseCaseImpl): ResetUseCase

    @Binds
    fun bindRecover(useCase: RecoverUseCaseImpl): RecoverUseCase

    @Binds
    fun bindSignUpUseCase(useCase: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    fun bindSignInUseCase(useCase: SignInUseCaseImpl): SignInUseCase
}
