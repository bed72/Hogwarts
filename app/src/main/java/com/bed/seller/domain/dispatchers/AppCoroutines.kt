package com.bed.seller.domain.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher

interface Coroutines {
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class AppCoroutines : Coroutines
