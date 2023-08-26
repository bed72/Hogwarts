package com.bed.seller.presentation.extension

import java.io.IOException
import java.io.InputStreamReader
import java.lang.IllegalStateException

import dagger.hilt.android.testing.HiltTestApplication

import androidx.test.platform.app.InstrumentationRegistry

@Suppress("RethrowCaughtException")
fun String.asJsonString(): String {
    try {
        val builder = StringBuilder()
        val inputStream = (InstrumentationRegistry.getInstrumentation().targetContext
            .applicationContext as HiltTestApplication).assets.open(this)
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach { builder.append(it) }

        return builder.toString()
    } catch (exception: IOException) { throw exception }
}
