package com.bed.seller.presentation.commons.connection

import android.app.Application
import android.content.Context

import androidx.lifecycle.LiveData

import android.net.Network
import android.net.NetworkRequest
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

abstract class CheckConnection : LiveData<Boolean>() {
    abstract val request: NetworkRequest
    abstract val network: ConnectivityManager.NetworkCallback
}

class CheckConnectionImpl(
    private val connectivity: ConnectivityManager
) : CheckConnection() {

    override val request: NetworkRequest
        get() = NetworkRequest.Builder().apply {
        addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }.build()

    constructor(application: Application) : this(
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

    override val network = object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()

        connectivity.registerNetworkCallback(request, network)
    }

    override fun onInactive() {
        super.onInactive()

        connectivity.unregisterNetworkCallback(network)
    }

}
