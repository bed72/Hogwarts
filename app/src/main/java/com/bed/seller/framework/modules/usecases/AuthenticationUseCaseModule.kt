package com.bed.seller.framework.modules.usecases

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import com.bed.core.usecases.authentication.ResetUsecase
import com.bed.core.usecases.authentication.ResetUsecaseImpl

import com.bed.core.usecases.authentication.SignUpUsecase
import com.bed.core.usecases.authentication.SignUpUsecaseImpl

import com.bed.core.usecases.authentication.SignInUsecase
import com.bed.core.usecases.authentication.SignInUsecaseImpl

import com.bed.core.usecases.authentication.RecoverUsecase
import com.bed.core.usecases.authentication.RecoverUsecaseImpl

import com.bed.core.usecases.authentication.IsLoggedInUsecase
import com.bed.core.usecases.authentication.IsLoggedInUsecaseImpl

import com.bed.core.usecases.authentication.SignOutUsecase
import com.bed.core.usecases.authentication.SignOutUsecaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface AuthenticationUseCaseModule {
    @Binds
    fun bindSignOut(useCase: SignOutUsecaseImpl): SignOutUsecase

    @Binds
    fun bindIsLoggedIn(useCase: IsLoggedInUsecaseImpl): IsLoggedInUsecase

    @Binds
    fun binReset(useCase: ResetUsecaseImpl): ResetUsecase

    @Binds
    fun bindRecover(useCase: RecoverUsecaseImpl): RecoverUsecase

    @Binds
    fun bindSignUpUseCase(useCase: SignUpUsecaseImpl): SignUpUsecase

    @Binds
    fun bindSignInUseCase(useCase: SignInUsecaseImpl): SignInUsecase
}
