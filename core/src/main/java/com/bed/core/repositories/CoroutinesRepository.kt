package com.bed.core.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher

interface CoroutinesRepository {
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}
