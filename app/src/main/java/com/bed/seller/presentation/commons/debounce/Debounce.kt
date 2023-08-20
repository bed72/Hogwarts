package com.bed.seller.presentation.commons.debounce

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

/**
 * Create by Faruk Toptaş
 * https://gist.github.com/faruktoptas/c45272047fae8da61acfb7b14c451793
 *
 * Thanks for help me
 */

fun <T> debounce(
    wait: Long = 400L,
    scope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null

    return { parameter: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(wait)
            destinationFunction(parameter)
        }
    }
}

fun debounce(
    wait: Long = 400L,
    scope: CoroutineScope,
    destinationFunction: () -> Unit
): () -> Unit {
    var debounceJob: Job? = null

    return {
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(wait)
            destinationFunction()
        }
    }
}
