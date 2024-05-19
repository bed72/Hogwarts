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

import com.bed.core.usecases.authentication.SignOutUsecase
import com.bed.core.usecases.authentication.SignOutUsecaseImpl

import com.bed.core.usecases.authentication.RecoverUsecase
import com.bed.core.usecases.authentication.RecoverUsecaseImpl

import com.bed.core.usecases.authentication.IsLoggedInUsecase
import com.bed.core.usecases.authentication.IsLoggedInUsecaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface AuthenticationUsecaseModule {
    @Binds
    fun bindSignOut(usecase: SignOutUsecaseImpl): SignOutUsecase

    @Binds
    fun bindIsLoggedIn(usecase: IsLoggedInUsecaseImpl): IsLoggedInUsecase

    @Binds
    fun binReset(usecase: ResetUsecaseImpl): ResetUsecase

    @Binds
    fun bindRecover(usecase: RecoverUsecaseImpl): RecoverUsecase

    @Binds
    fun bindSignUp(usecase: SignUpUsecaseImpl): SignUpUsecase

    @Binds
    fun bindSignIn(usecase: SignInUsecaseImpl): SignInUsecase
}
