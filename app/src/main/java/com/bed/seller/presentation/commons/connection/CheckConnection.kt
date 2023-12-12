package com.bed.seller.presentation.commons.connection

import android.app.Application
import android.content.Context

import android.net.Network
import android.net.NetworkRequest
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.core.content.ContextCompat.getSystemService

abstract class CheckConnection : DefaultLifecycleObserver {
    abstract val state: StateFlow<States>
    abstract val isActiveNetworkMetered: Boolean

    sealed class States {
        data object Initial : States()
        data class IsConnected(val isConnected: Boolean) : States()
    }
}

class CheckConnectionImpl(
    private val context: Context,
    private val connectivity: ConnectivityManager
) : CheckConnection() {
    private val _state = MutableStateFlow<States>(States.Initial)
    override val state: StateFlow<States> get() = _state.asStateFlow()
    override val isActiveNetworkMetered: Boolean get() = manager.isActiveNetworkMetered

    constructor(application: Application) : this(
        application,
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

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

            _state.update { States.IsConnected(true) }
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            _state.update { States.IsConnected(false) }
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        connectivity.registerNetworkCallback(request , network)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)

        connectivity.unregisterNetworkCallback(network)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        connectivity.unregisterNetworkCallback(network)
    }
}
