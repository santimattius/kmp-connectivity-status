package com.santimattius.kmp.network.checker

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow

actual class ConnectivityStatus(
    private val context: Context
) : ConnectivityManager.NetworkCallback() {

    actual val isNetworkConnected = MutableStateFlow(false)

    private var connectivityManager: ConnectivityManager? = null

    override fun onAvailable(network: Network) {
        isNetworkConnected.value = true
    }

    override fun onLost(network: Network) {
        isNetworkConnected.value = false
    }

    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)

        val isConnected =
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

        isNetworkConnected.value = isConnected
    }

    actual fun start() {
        try {
            if (connectivityManager == null) {
                connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            }

            connectivityManager?.registerDefaultNetworkCallback(this)

            val currentNetwork = connectivityManager?.activeNetwork

            if (currentNetwork == null) {
                isNetworkConnected.value = false
            }
        } catch (e: Exception) {
            isNetworkConnected.value = false
        }
    }

    actual fun stop() {
        connectivityManager?.unregisterNetworkCallback(this)
    }
}