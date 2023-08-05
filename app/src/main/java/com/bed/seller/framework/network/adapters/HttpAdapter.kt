package com.bed.seller.framework.network.adapters

import javax.inject.Inject

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

interface HttpAdapter {
    val ktor: HttpClient
}

class HttpAdapterImpl @Inject constructor() : HttpAdapter {

    override val ktor get() = HttpClient(OkHttp) {
        adapterLogging()
        adapterRequestDefault()
        adapterResponseTimeout()
        adapterResponseObserver()
        adapterContentNegotiation()
    }
}
