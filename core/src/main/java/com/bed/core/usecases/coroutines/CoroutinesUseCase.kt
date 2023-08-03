package com.bed.core.usecases.coroutines

import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher

interface CoroutinesUseCase {
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class CoroutinesUseCaseImpl @Inject constructor() : CoroutinesUseCase
