package com.bed.seller.presentation.commons.connection

import android.app.Application
import android.content.Context

import android.net.Network
import android.net.NetworkRequest
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

import androidx.lifecycle.LiveData
import androidx.core.content.ContextCompat.getSystemService

abstract class CheckConnection : LiveData<Boolean>() {
    abstract val isActiveNetworkMetered: Boolean
}

class CheckConnectionImpl(
    private val context: Context,
    private val connectivity: ConnectivityManager
) : CheckConnection() {
    constructor(application: Application) : this(
        application,
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

    override val isActiveNetworkMetered: Boolean get() = manager.isActiveNetworkMetered

    private val manager: ConnectivityManager
        get() = (getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager)
            .apply { requestNetwork(request, network) }

    private val request: NetworkRequest get() = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val network = object : ConnectivityManager.NetworkCallback() {
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

        connectivity.registerNetworkCallback(request , network)
    }

    override fun onInactive() {
        super.onInactive()

        connectivity.unregisterNetworkCallback(network)
    }
}
