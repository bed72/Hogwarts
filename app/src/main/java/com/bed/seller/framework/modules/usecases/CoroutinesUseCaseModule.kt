package com.bed.seller.framework.modules.usecases

import com.bed.core.usecases.coroutines.CoroutinesUseCase
import com.bed.core.usecases.coroutines.CoroutinesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CoroutinesUseCaseModule {
    @Binds
    fun bindCoroutinesUseCase(useCase: CoroutinesUseCaseImpl): CoroutinesUseCase
}
